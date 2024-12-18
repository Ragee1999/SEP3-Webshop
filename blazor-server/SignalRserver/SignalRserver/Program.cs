using Microsoft.AspNetCore.Builder;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Hosting;
using SignalRserver.hub;
using Stripe;

var builder = WebApplication.CreateBuilder(args);

// Load Stripe configuration from appsettings.json
var stripeSection = builder.Configuration.GetSection("Stripe");
StripeConfiguration.ApiKey = stripeSection["SecretKey"]; // Properly load Stripe Secret Key


builder.Services.AddControllers(); // For handling REST API controllers
builder.Services.AddSignalR();    // For SignalR functionality
builder.Services.AddHttpClient(); // for Server to server connection (Rest with SignalR)


builder.Services.AddCors(options =>
{
    options.AddDefaultPolicy(policy =>
    {
        policy.WithOrigins("http://localhost:5203", "https://localhost:7237", "http://localhost:8080") 
            .AllowAnyHeader()
            .AllowAnyMethod()
            .AllowCredentials();
    });
});



var app = builder.Build();

// Configure the HTTP request pipeline
if (app.Environment.IsDevelopment())
{
    app.UseDeveloperExceptionPage(); // Better error display in development
}
else
{
    app.UseExceptionHandler("/Error");
    app.UseHsts();
}

app.UseHttpsRedirection();
app.UseRouting();
app.UseCors(); // Enable CORS
app.MapGet("/", () => "SignalR Server is running");

// Map SignalR Hub and Controllers
app.MapHub<PaymentHub>("/paymenthub"); 
app.MapControllers(); 

app.Run();