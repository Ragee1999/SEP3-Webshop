using Microsoft.AspNetCore.SignalR;
using Moq;
using SignalRserver.hub;
using System.Net;
using Xunit;

public class PaymentHubTest
{
    private readonly Mock<IHubCallerClients> _mockClients;
    private readonly Mock<IClientProxy> _mockClientProxy;
    private readonly Mock<HttpMessageHandler> _mockHttpMessageHandler;
    private readonly HttpClient _mockHttpClient;

    public PaymentHubTest()
    {
        _mockClients = new Mock<IHubCallerClients>();
        _mockClientProxy = new Mock<IClientProxy>();
        _mockHttpMessageHandler = new Mock<HttpMessageHandler>(MockBehavior.Strict);

        _mockHttpClient = new HttpClient(new MockHttpMessageHandler()) { BaseAddress = new System.Uri("https://localhost:7237") };
    }

    [Fact]
    public async Task NotifyPaymentSuccess_ShouldSendOrderDetailsToApiAndClients()
    {
        // Arrange
        var sessionId = "test-session-id";
        var paymentHub = new PaymentHub(_mockHttpClient)
        {
            Clients = _mockClients.Object
        };

        _mockClients.Setup(clients => clients.All).Returns(_mockClientProxy.Object);

        // Act
        await paymentHub.NotifyPaymentSuccess(sessionId);

        // Assert
        _mockClientProxy.Verify(
            client => client.SendCoreAsync(
                "ReceivePaymentDetails",
                It.IsAny<object[]>(),
                default),
            Times.Once);
    }
}

public class MockHttpMessageHandler : HttpMessageHandler
{
    protected override Task<HttpResponseMessage> SendAsync(HttpRequestMessage request, System.Threading.CancellationToken cancellationToken)
    {
        return Task.FromResult(new HttpResponseMessage
        {
            StatusCode = HttpStatusCode.OK,
            Content = new StringContent("Success")
        });
    }
}