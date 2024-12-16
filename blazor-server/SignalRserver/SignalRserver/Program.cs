using Microsoft.AspNetCore.Components;
using Microsoft.AspNetCore.Components.Web;
using SignalRserver.Data;
using Stripe;

// Create and configure the WebApplication
var builder = WebApplication.CreateBuilder(args);

// Load Stripe configuration from appsettings.json
var stripeSection = builder.Configuration.GetSection("Stripe");
StripeConfiguration.ApiKey = stripeSection["SecretKey"]; // Properly load Stripe Secret Key

// Add services to the container
builder.Services.AddRazorPages();
builder.Services.AddServerSideBlazor();
builder.Services.AddSingleton<WeatherForecastService>();

var app = builder.Build();

// Configure the HTTP request pipeline
if (!app.Environment.IsDevelopment())
{
    app.UseExceptionHandler("/Error");
    app.UseHsts();
}

app.UseHttpsRedirection();
app.UseStaticFiles();
app.UseRouting();

// Map Blazor SignalR Hub
app.MapBlazorHub();
app.MapFallbackToPage("/_Host");

app.Run();