using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.SignalR;
using SignalRserver.hub;

namespace SignalRserver.Controller
{
    [ApiController]
    [Route("api/notifications")]
    public class NotificationController : ControllerBase
    {
        private readonly IHubContext<PaymentHub> _hubContext;

        public NotificationController(IHubContext<PaymentHub> hubContext)
        {
            _hubContext = hubContext;
        }

        [HttpPost]
        public async Task<IActionResult> NotifyPaymentStatus([FromBody] PaymentNotification notification)
        {
            // Broadcast the payment status to SignalR clients
            await _hubContext.Clients.All.SendAsync("ReceivePaymentStatus", notification.SessionId, notification.Status);
            return Ok();
        }
        [HttpGet("payment-success")]
        public async Task<IActionResult> NotifyPaymentSuccess([FromQuery] string sessionId)
        {
            if (string.IsNullOrEmpty(sessionId))
            {
                return BadRequest("Session ID is missing.");
            }

            // Send notification to SignalR clients
            await _hubContext.Clients.All.SendAsync("ReceivePaymentStatus", sessionId, "Payment Successful");

            return Ok("Payment notification sent.");
        }
    }

    public class PaymentNotification
    {
        public string SessionId { get; set; }
        public string Status { get; set; }
    }
}