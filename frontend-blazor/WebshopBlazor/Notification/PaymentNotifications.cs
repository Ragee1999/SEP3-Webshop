using Microsoft.AspNetCore.Components;
using Microsoft.AspNetCore.SignalR.Client;

namespace WebshopBlazor.Notification
{
    public class PaymentNotifications : IAsyncDisposable
    {
        private readonly NavigationManager _navigationManager;
        private HubConnection? _hubConnection;
        private string _notificationMessage = "Waiting for payment updates...";
        private bool _isConnected = false;

        public string NotificationMessage => _notificationMessage;

        public PaymentNotifications(NavigationManager navigationManager)
        {
            _navigationManager = navigationManager;

            // Initialize SignalR connection
            _hubConnection = new HubConnectionBuilder()
                .WithUrl(_navigationManager.ToAbsoluteUri("https://localhost:7237/paymenthub")) 
                .WithAutomaticReconnect() // Ensure reconnection attempts
                .Build();

            // Subscribe to SignalR events
            _hubConnection.On<string, string>("ReceivePaymentStatus", (sessionId, status) =>
            {
                _notificationMessage = $"Payment Status Update: Session {sessionId} - {status}";
                NotifyStateChanged();
            });
        }

        public async Task StartConnectionAsync(TimeSpan timeout)
        {
            if (_hubConnection is not null)
            {
                try
                {
                    var connectionTask = _hubConnection.StartAsync();
                    if (await Task.WhenAny(connectionTask, Task.Delay(timeout)) == connectionTask)
                    {
                        _isConnected = true;
                        await connectionTask;
                    }
                    else
                    {
                        _notificationMessage = "Failed to connect: Timeout reached.";
                        NotifyStateChanged();
                    }
                }
                catch (Exception ex)
                {
                    _notificationMessage = $"Failed to connect: {ex.Message}";
                    NotifyStateChanged();
                }
            }
        }

        public async ValueTask DisposeAsync()
        {
            if (_hubConnection is not null)
            {
                await _hubConnection.DisposeAsync();
            }
        }

        public bool IsConnected => _isConnected;

        public event Action? OnChange;

        private void NotifyStateChanged() => OnChange?.Invoke();
    }
}
