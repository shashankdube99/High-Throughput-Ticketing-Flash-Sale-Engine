# High-Throughput-Ticketing-Flash-Sale-Engine

Part 1: The User Journey (How it Works in Practice)
Let's use a real-world example: A Flash Sale for a Taylor Swift Concert with only 100 tickets available.

Step 1: The Pre-Sale Browsing
What the User Does: The user logs into the ticketing app 10 minutes before the sale starts. They view the event details page.

Behind the Scenes: The system doesn't touch the slow PostgreSQL database to show the event details. Instead, it serves the event page instantly from a Redis Cache.

Step 2: The Ticket Drop (The Rush hour)
What the User Does: The clock hits 8:00 PM. The "Buy Ticket" button turns green. The user and 50,000 other fans click "Buy" at the exact same millisecond.

Behind the Scenes: Instead of letting 50,000 requests hit the database (which would crash it instantly), Nginx distributes the traffic smoothly across multiple running instances of your Spring Boot app.

Step 3: The Instant Verdict
What the User Does: Within 50 milliseconds, the user sees one of two things on their screen:

Success: "Your spot is secured! We are processing your ticket..."

Sold Out: "Sorry, tickets are sold out!" (HTTP 410 Gone).

Behind the Scenes: The Spring Boot app checks Redis using an atomic operation (DECR). Redis acts as a high-speed gatekeeper. If the count goes below 0, it instantly rejects the user without hurting the database.

Step 4: The Order Confirmation
What the User Does: The successful user waits a brief moment on a loading screen, then receives their confirmed ticket ID and receipt.

Behind the Scenes: While the user was looking at the success screen, Kafka was quietly passing their order details to a background worker that wrote the data permanently into PostgreSQL safely and sequentially.

Part 2: Step-by-Step System Flow (The Tech Mechanics)
To understand how to build and test this project end-to-end, follow these logical phases of data movement.

Phase 1: Preloading the Ammo (Application Startup)
Before the user even opens the app, your system needs to be ready.

When the Spring Boot application boots up, an asynchronous startup method triggers.

It queries PostgreSQL: "How many tickets are available for Event X?" (Let's say 100).

It stores this number in Redis: SET event:1001:stock 100.
Now, Redis is armed and ready to handle the high-speed traffic.

Phase 2: High-Speed Validation (The Gatekeeper)
When a user clicks "Buy":

The request passes through Nginx to a Java 21 Virtual Thread in Spring Boot. Virtual threads are incredibly lightweight, meaning your app can handle thousands of concurrent connections without running out of memory.

The app asks Redis to decrement the stock using Redisson:

Java
Long remainingStock = redis.decrement("event:1001:stock");
The Logic Split:

If remainingStock >= 0: The user grabbed a ticket! The system immediately generates a temporary Order ID and sends a success response to the user.

If remainingStock < 0: The ticket is gone. The system reverts the count and immediately throws an exception, returning an HTTP 410 error to the user.

Phase 3: The Async Queue (Decoupling the Database)
Writing to a hard drive (Relational Database) is 100x slower than writing to RAM (Redis). If we forced the user to wait for PostgreSQL to finish saving, the entire system would back up and crash.

Once Redis confirms the stock deduction, the web thread does not talk to PostgreSQL.

Instead, it drops a tiny JSON message into a Kafka Topic (e.g., ticket-orders):

JSON
{ "userId": 543, "eventId": 1001, "timestamp": "2026-05-26T13:13:18Z" }
The web thread is now totally free to handle the next user request in line.

Phase 4: Smooth Database Persistence
Behind the scenes, a dedicated background worker (@KafkaListener) listens to that Kafka topic.

It pulls messages off the queue at a steady, manageable pace that PostgreSQL can handle.

To maximize efficiency, it groups these messages together and performs a Batch Insert into the database, officially saving the records permanently.

Phase 5: Verification & Stress Testing (Proving the SLA)
To prove that your system meets the strict requirements in your MOU (1,000+ Requests Per Second at less than 100ms latency):

You spin up Apache JMeter or Gatling.

You configure it to simulate 5,000 simultaneous users hitting your checkout endpoint in a 5-second window.

You monitor Spring Boot Actuator to check metrics like CPU usage, active virtual threads, and database connection pool health.

Finally, you run a SQL query on PostgreSQL:

SQL
SELECT COUNT(*) FROM orders WHERE event_id = 1001;
If the count is exactly 100, and your error rate is 0%, your system successfully achieved a 0% overselling policy under maximum load!
