# High-Throughput-Ticketing-Flash-Sale-Engine

event-booking-platform/
├── .github/                     # CI/CD workflows (GitHub Actions)
├── docker-compose.yml           # Spins up local PostgreSQL, Redis, Kafka, ElasticSearch
├── Makefile                     # Shortcut commands for building, seeding, and testing
│
├── apps/                        # The executable applications
│   ├── api-gateway/             # Gateway config, rate limiter, and route manager
│   └── backend-monolith/        # Main server housing our LLD core modules
│       ├── src/
│       │   ├── main.go / Main.java / index.ts (Application entry point)
│       │   │
│       │   └── modules/         # Clean domain boundaries matching LLD services
│       │
│       │       ├── search/      # Handles /v1/search
│       │       │   ├── controller.go   # HTTP Request/Response validation
│       │       │   ├── service.go      # Business logic for query formatting
│       │       │   └── repository.go   # Direct communication with ElasticSearch
│       │       │
│       │       ├── events/      # Handles /v1/event/{id}
│       │       │   ├── controller.go   # Handles fetch and admin event creation
│       │       │   ├── service.go      # Formats event payload & seats data
│       │       │   └── repository.go   # Read/Writes to NoSQL (Cassandra/Scylla)
│       │       │
│       │       └── booking/     # Handles /v1/booking/reserve & /confirm
│       │           ├── controller.go   # Accepts requests, returns booking IDs
│       │           ├── service.go      # Execution engine for transactions & locks
│       │           ├── cache_repo.go   # Handles atomic Redis commands (10-min hold)
│       │           ├── db_repo.go      # Handles ACID PostgreSQL ticket tables
│       │           └── kafka_prod.go   # Pushes successful payments onto the queue
│       │
│       └── shared/              # Reusable code across modules
│           ├── database/        # DB Connection pool initializers (Postgres, Redis)
│           ├── middleware/      # Auth payload parsers, structured logger
│           └── eventbus/        # Internal event publishers (for immediate communication)
│
├── infrastructure/              # Local development configurations
│   ├── postgres/                # Initialization SQL schemas and migrations
│   ├── kafka/                   # Topic provisioning scripts
│   └── elasticsearch/           # Index structure and mapping JSONs
│
└── scripts/                     # Local developer automation
    ├── seed_data.sh             # Seeds 10,000 events & seats for local testing
    └── load_test.js             # k6 or Locust scripts for concurrency testing
