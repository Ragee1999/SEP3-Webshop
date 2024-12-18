using Microsoft.AspNetCore.SignalR;
using Stripe;
using Stripe.Checkout;
using System.Net.Http.Json;

namespace SignalRserver.hub
{
    public class PaymentHub : Hub
    {
        private readonly HttpClient _httpClient;

        public PaymentHub(HttpClient httpClient)
        {
            _httpClient = httpClient;
        }

        public async Task NotifyPaymentSuccess(string sessionId)
        {
            try
            {
                // Set Stripe API key
                StripeConfiguration.ApiKey = "sk_test_51QSbQTFzrlRAcoaO3Q9F6m6WjSlp3g9k54n5AeHPrNBTxWIKPgY5NUaixlKAq208LklRJQAnCLzNwoVM3XVnSUXp00E9Nwdsgg";

                // Fetch Stripe session details
                var sessionService = new SessionService();
                var session = sessionService.Get(sessionId);

                // Fetch line items (products in the order)
                var lineItemService = new SessionLineItemService();
                var lineItems = lineItemService.List(sessionId);

                // Prepare list of order items
                var orderItems = lineItems.Data.Select(item => new
                {
                    Name = item.Description,
                    Quantity = item.Quantity,
                    Price = (item.AmountTotal / 100.0) / item.Quantity
                }).ToList();

                // Prepare OrderHistory object
                var orderHistory = new
                {
                    SessionId = session.Id,
                    CustomerName = session.CustomerDetails?.Name ?? "Unknown",
                    Email = session.CustomerDetails?.Email ?? "Unknown",
                    AmountPaid = session.AmountTotal / 100.0,
                    Address = session.CustomerDetails?.Address != null
                        ? $"{session.CustomerDetails.Address.Line1}, {session.CustomerDetails.Address.City}, {session.CustomerDetails.Address.PostalCode}, {session.CustomerDetails.Address.Country}"
                        : "No Address",
                    Items = orderItems // List of OrderItem objects
                };

                // Send orderHistory to the backend REST API
                var response = await _httpClient.PostAsJsonAsync("http://localhost:8080/api/orderhistory/save", orderHistory);

                if (!response.IsSuccessStatusCode)
                {
                    Console.WriteLine($"Error saving order to database: {response.ReasonPhrase}");
                }

                // Broadcast to SignalR clients
                await Clients.All.SendAsync("ReceivePaymentDetails", orderHistory);
            }
            catch (Exception ex)
            {
                Console.WriteLine($"Error in NotifyPaymentSuccess: {ex.Message}");
            }
        }

        public async Task FetchStripeData()
        {
            try
            {
                StripeConfiguration.ApiKey = "sk_test_51QSbQTFzrlRAcoaO3Q9F6m6WjSlp3g9k54n5AeHPrNBTxWIKPgY5NUaixlKAq208LklRJQAnCLzNwoVM3XVnSUXp00E9Nwdsgg";

                var sessionService = new SessionService();
                var sessions = sessionService.List(new SessionListOptions { Limit = 100 });

                var lineItemService = new SessionLineItemService();
                var completedPayments = sessions.Data.Where(s => s.PaymentStatus == "paid")
                    .Select(session =>
                    {
                        var lineItems = lineItemService.List(session.Id);
                        var orderItems = lineItems.Data.Select(item => new
                        {
                            Name = item.Description,
                            Quantity = item.Quantity,
                            Price = (item.AmountTotal / 100.0) / item.Quantity
                        }).ToList();

                        return new
                        {
                            SessionId = session.Id,
                            CustomerName = session.CustomerDetails?.Name ?? "Unknown",
                            Email = session.CustomerDetails?.Email ?? "Unknown",
                            AmountPaid = session.AmountTotal / 100.0,
                            Address = session.CustomerDetails?.Address != null
                                ? $"{session.CustomerDetails.Address.Line1}, {session.CustomerDetails.Address.City}, {session.CustomerDetails.Address.PostalCode}, {session.CustomerDetails.Address.Country}"
                                : "No Address",
                            Items = orderItems
                        };
                    }).ToList();

                // Broadcast payment details to clients
                await Clients.All.SendAsync("ReceivePaymentDetails", completedPayments);
            }
            catch (StripeException ex)
            {
                Console.WriteLine($"Error fetching Stripe data: {ex.Message}");
            }
        }
    }
}
