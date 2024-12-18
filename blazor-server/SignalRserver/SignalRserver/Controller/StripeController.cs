using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.SignalR;
using Stripe;
using SignalRserver.hub;

namespace SignalRserver.controller
{
    [Route("api/stripe")]
    [ApiController]
    public class StripeController : ControllerBase
    {
        private readonly IHubContext<PaymentHub> _hubContext;

        public StripeController(IHubContext<PaymentHub> hubContext)
        {
            _hubContext = hubContext;
        }

        [HttpGet("fetch-payment-details")]
        public IActionResult FetchPaymentDetails()
        {
            try
            {
                // Configure Stripe
                StripeConfiguration.ApiKey = "sk_test_51QSbQTFzrlRAcoaO3Q9F6m6WjSlp3g9k54n5AeHPrNBTxWIKPgY5NUaixlKAq208LklRJQAnCLzNwoVM3XVnSUXp00E9Nwdsgg";

                // Fetch all succeeded charges
                var chargeService = new ChargeService();
                var charges = chargeService.List(new ChargeListOptions { Limit = 100 });

                // Process succeeded charges
                var completedPayments = charges.Data
                    .Where(charge => charge.Status == "succeeded")
                    .Select(charge => new
                    {
                        Id = charge.Id,
                        Email = charge.BillingDetails?.Email ?? "Unknown",
                        AmountPaid = charge.Amount / 100.0, // Convert from cents to dollars
                        Currency = charge.Currency.ToUpper()
                    })
                    .ToList();

                // Broadcast data to SignalR clients
                _hubContext.Clients.All.SendAsync("ReceivePaymentDetails", completedPayments);

                return Ok(completedPayments);
            }
            catch (StripeException ex)
            {
                Console.WriteLine($"Error fetching payment details: {ex.Message}");
                return BadRequest($"Error fetching payment details: {ex.Message}");
            }
        }
    }
}