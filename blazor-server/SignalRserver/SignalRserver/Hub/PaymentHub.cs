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
                StripeConfiguration.ApiKey = "sk_test_51QSbQTFzrlRAcoaO3Q9F6m6WjSlp3g9k54n5AeHPrNBTxWIKPgY5NUaixlKAq208LklRJQAnCLzNwoVM3XVnSUXp00E9Nwdsgg";

                var sessionService = new SessionService();
                var session = sessionService.Get(sessionId);

                var paymentDetails = new
                {
                    SessionId = session.Id,
                    Email = session.CustomerDetails?.Email ?? "Unknown",
                    AmountPaid = session.AmountTotal / 100.0,
                    Currency = session.Currency.ToUpper(),
                    Status = "Paid"
                };

                // Broadcast only minimal details to clients
                await Clients.All.SendAsync("ReceivePaymentDetails", paymentDetails);
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
                StripeConfiguration.ApiKey =
                    "sk_test_51QSbQTFzrlRAcoaO3Q9F6m6WjSlp3g9k54n5AeHPrNBTxWIKPgY5NUaixlKAq208LklRJQAnCLzNwoVM3XVnSUXp00E9Nwdsgg";

                var sessionService = new SessionService();
                var sessions = sessionService.List(new SessionListOptions { Limit = 100 });

                var lineItemService = new SessionLineItemService();
                var completedPayments = sessions.Data.Where(s => s.PaymentStatus == "paid")
                    .Select(session =>
                    {
                        var lineItems = lineItemService.List(session.Id);

                        return new
                        {
                            Id = session.Id,
                            CustomerName = session.CustomerDetails?.Name ?? "Unknown",
                            Email = session.CustomerDetails?.Email ?? "Unknown",
                            AmountPaid = session.AmountTotal / 100.0,
                            Address = session.CustomerDetails?.Address != null
                                ? $"{session.CustomerDetails.Address.Line1}, {session.CustomerDetails.Address.City}, {session.CustomerDetails.Address.PostalCode}, {session.CustomerDetails.Address.Country}"
                                : "No Address",
                            Items = lineItems.Data.Select(item => new
                            {
                                Name = item.Description,
                                Quantity = item.Quantity,
                                Price = (item.AmountTotal / 100.0) / item.Quantity
                            }).ToList()
                        };
                    }).ToList();

                // Broadcast payment details to all clients (dashboard use case)
                await Clients.All.SendAsync("ReceivePaymentDetails", completedPayments);

                // Do not save data to backend here
            }
            catch (StripeException ex)
            {
                Console.WriteLine($"Error fetching Stripe data: {ex.Message}");
            }
        }
    }
}
