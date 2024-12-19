using Microsoft.AspNetCore.SignalR.Client;
using System;
using System.Threading.Tasks;
using Xunit;

namespace SignalRserver.Test
{
    public class SignalRIntegrationTest
    {
        private readonly string _serverUrl = "https://localhost:7237/paymenthub"; 
        private HubConnection _connection;

        public SignalRIntegrationTest()
        {
            // Configure the HubConnection manually
            _connection = new HubConnectionBuilder()
                .Build();
        }

        [Fact]
        public async Task TestConnectionToServer()
        {
            // Arrange
            bool connectionEstablished = false;
            _connection.Closed += ex =>
            {
                Console.WriteLine("Connection closed: " + ex?.Message);
                return Task.CompletedTask;
            };

            // Act
            try
            {
                await _connection.StartAsync();
                connectionEstablished = _connection.State == HubConnectionState.Connected;
            }
            catch (Exception ex)
            {
                Console.WriteLine("Error connecting to SignalR server: " + ex.Message);
            }

            // Assert
            Assert.True(connectionEstablished, "Failed to establish connection to SignalR server.");

            // Cleanup
            await _connection.StopAsync();
        }

        [Fact]
        public async Task TestReceiveMessageFromServer()
        {
            // Arrange
            string expectedMessage = "Test Message";
            string receivedMessage = null;

            _connection.On<string>("ReceiveTestMessage", message =>
            {
                receivedMessage = message;
            });

            // Act
            try
            {
                await _connection.StartAsync();
                await Task.Delay(500); 
            }
            catch (Exception ex)
            {
                Console.WriteLine("Error during message test: " + ex.Message);
            }

            // Assert
            Assert.Equal(expectedMessage, receivedMessage);

            // Cleanup
            await _connection.StopAsync();
        }
    }
}
