# GT Pekseg Global Enterprise Pricing Service

Welcome to the team!

This is the product pricing service for the GT Pekseg Global Enterprise shop. It calculates final
customer prices, applies discounts and campaigns, handles tax by country,
sends notifications, and generates reports.

It already works and is in production. Your job this sprint is to work through
a backlog of small, isolated business change requests.

---

## Running it

Requirements: JDK 21 (toolchain configured — any JDK 21+ distribution works).

```bash
./gradlew bootRun      # starts on http://localhost:8080
./gradlew test         # runs the test suite
```

### The API

**POST /price/calculate** — calculate a final price.

```bash
curl -X POST http://localhost:8080/price/calculate \
  -H "Content-Type: application/json" \
  -d '{
        "customerId":       "C001",
        "customerType":     "VIP",
        "productCategory":  "BOOK",
        "basePrice":        100,
        "country":          "US",
        "campaign":         "HOLIDAY",
        "reportFormat":     "JSON",
        "notificationType": "EMAIL",
        "loyaltyPoints":    200
      }'
```

Response:

```json
{ "finalPrice": 70.2 }
```

The endpoint path and the JSON field names in the request and response are a
**fixed contract**. Do not change them. The test suite hits this URL with these
exact field names.

---

## Where you work

You do **not** need to know Spring, or any web framework.

```
com.gtpeksegglobalenterprise.pricing.api/        OFF-LIMITS. Do not open or modify.
                                                  (HTTP plumbing. Already done. Not your problem.)

com.gtpeksegglobalenterprise.pricing.exceptions/
    ValidationException       Throw this for bad input — it maps to HTTP 400
                              with a structured error body automatically.

com.gtpeksegglobalenterprise.pricing.dto/        OFF-LIMITS. Fixed JSON wire contracts.
    PriceRequestDto           Do not rename or remove any field.
    PriceResponseDto          Do not rename or remove any field.

com.gtpeksegglobalenterprise.pricing.models/     YOUR WORK. Weak starting models — improve them
                                                  as the tickets demand it.
    Customer                  Domain model. Currently a bag of strings.
    Product                   Domain model. Currently a bag of strings.
    Order                     Domain model. Currently a bag of strings.
    PricingConfig             Runtime pricing limits. Yours to extend.

com.gtpeksegglobalenterprise.pricing.services/   YOUR WORK. The god class lives here.
    PricingService            ~300 lines doing everything. Your main target.
```

The web layer creates your service with `new PricingService(new PricingConfig())`
and calls one method: `calculate(PriceRequestDto)`. That is the only fixed
contract. Everything behind it — and everything in
`com.gtpeksegglobalenterprise.pricing.models/` and
`com.gtpeksegglobalenterprise.pricing.services/` — is yours to redesign.

---

## Your backlog this sprint

Implement **all** of the ```/tickets``` folder. They are ordered the way the product
manager prioritised them, not by difficulty.

---

> **Note from the tech lead:** The fastest way to close each ticket is to add
> another `else if` branch inside `PricingService`. Please don't. By TICKET-04
> the discount logic will be unmaintainable, and by TICKET-09 the report switch
> will be a nightmare. The tickets are designed to hurt if you don't refactor —
> that is the point. If your diff makes `PricingService` *longer*, you are
> almost certainly going the wrong way.
