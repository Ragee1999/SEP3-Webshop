using Blazored.LocalStorage;
using Microsoft.AspNetCore.Components.Web;
using Microsoft.AspNetCore.Components.WebAssembly.Hosting;
using Microsoft.AspNetCore.SignalR.Client;
using WebshopBlazor;
using WebshopBlazor.Notification;

var builder = WebAssemblyHostBuilder.CreateDefault(args);
builder.RootComponents.Add<App>("#app");
builder.RootComponents.Add<HeadOutlet>("head::after");

builder.Services.AddScoped(sp => new HttpClient { BaseAddress = new Uri("http://localhost:8080") });
builder.Services.AddBlazoredLocalStorage();
builder.Services.AddScoped<PaymentNotifications>();

builder.Services.AddSingleton(sp => new HubConnectionBuilder()
    .WithUrl("https://localhost:7237/paymenthub")
    .WithAutomaticReconnect()
    .Build());

await builder.Build().RunAsync();