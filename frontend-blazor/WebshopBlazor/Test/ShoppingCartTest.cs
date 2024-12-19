using System.Net;
using System.Net.Http;
using System.Net.Http.Json;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Components;
using Moq;
using Moq.Protected;
using Xunit;

namespace WebshopBlazor.Test
{
    public class ShoppingCartTest
    {
        [Fact]
        public async Task Checkout_ShouldPostCartItems_AndRedirectToStripeSession()
        {
            // Arrange
            var cartItems = new List<ShoppingCart.CartItem>
            {
                new ShoppingCart.CartItem { Id = 1, Name = "Test Product", Price = 10.0m, Quantity = 2 }
            };

            // Mock HttpMessageHandler
            var mockHttpMessageHandler = new Mock<HttpMessageHandler>();

            mockHttpMessageHandler.Protected()
                .Setup<Task<HttpResponseMessage>>(
                    "SendAsync",
                    ItExpr.IsAny<HttpRequestMessage>(),
                    ItExpr.IsAny<CancellationToken>())
                .ReturnsAsync(new HttpResponseMessage
                {
                    StatusCode = HttpStatusCode.OK,
                    Content = new StringContent("\"https://checkout.stripe.com/session_id\"")
                });

            var httpClient = new HttpClient(mockHttpMessageHandler.Object)
            {
                BaseAddress = new Uri("https://localhost:5203") 
            };

            // Mock NavigationManager
            var mockNavigationManager = new Mock<NavigationManager>();

            mockNavigationManager.Setup(nav => nav.NavigateTo(It.IsAny<string>(), true));

            // Create ShoppingCart instance
            var shoppingCart = new ShoppingCart(httpClient, mockNavigationManager.Object)
            {
                CartItems = cartItems
            };

            // Act
            await shoppingCart.Checkout();

            // Assert
            mockHttpMessageHandler.Protected().Verify(
                "SendAsync",
                Times.Once(),
                ItExpr.Is<HttpRequestMessage>(req =>
                    req.Method == HttpMethod.Post &&
                    req.RequestUri.ToString() == "api/stripe/create-checkout-session" &&
                    req.Content != null),
                ItExpr.IsAny<CancellationToken>());

            mockNavigationManager.Verify(nav =>
                nav.NavigateTo("https://checkout.stripe.com/session_id", true), Times.Once);
        }
    }

    public class ShoppingCart
    {
        private readonly HttpClient _httpClient;
        private readonly NavigationManager _navigationManager;

        public ShoppingCart(HttpClient httpClient, NavigationManager navigationManager)
        {
            _httpClient = httpClient;
            _navigationManager = navigationManager;
        }

        public List<CartItem> CartItems { get; set; }

        public async Task Checkout()
        {
            var response = await _httpClient.PostAsJsonAsync("api/stripe/create-checkout-session", CartItems);

            if (response.IsSuccessStatusCode)
            {
                var sessionUrl = await response.Content.ReadAsStringAsync();
                _navigationManager.NavigateTo(sessionUrl, true);
            }
        }

        public class CartItem
        {
            public long Id { get; set; }
            public string Name { get; set; }
            public decimal Price { get; set; }
            public int Quantity { get; set; }
        }
    }
}
