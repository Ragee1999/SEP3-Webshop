using Microsoft.AspNetCore.SignalR;
using Microsoft.AspNetCore.Mvc;
using Moq;
using SignalRserver.Controller;
using SignalRserver.hub;
using Xunit;

namespace SignalRserver.Test
{
    public class NotificationControllerTest
    {
        [Fact]
        public async Task NotifyPaymentStatus_ShouldBroadcastPaymentStatus()
        {
            // Arrange
            var mockHubContext = new Mock<IHubContext<PaymentHub>>();
            var mockClients = new Mock<IHubClients>();
            var mockClientProxy = new Mock<IClientProxy>();

            // Setup mock behavior for SignalR context and clients
            mockHubContext.Setup(hub => hub.Clients).Returns(mockClients.Object);
            mockClients.Setup(clients => clients.All).Returns(mockClientProxy.Object);

            var controller = new NotificationController(mockHubContext.Object);
            var notification = new PaymentNotification
            {
                SessionId = "1",
                Status = "Paid"
            };

            // Act
            var result = await controller.NotifyPaymentStatus(notification);

            // Assert
            mockClientProxy.Verify(
                client => client.SendCoreAsync(
                    "ReceivePaymentStatus",
                    It.Is<object[]>(o =>
                        (string)o[0] == "123" && (string)o[1] == "Paid"
                    ),
                    default
                ),
                Times.Once
            );

            Assert.IsType<OkResult>(result);
        }
    }
}