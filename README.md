# KotlinRepo

This app list the top ones GitHub repositories which use Kotlin, are public and starred at least 1000 times.

## Architecture

It's a MVVM-fragmentless-single activity app + clean architecture   

### Layers
UI: Activity - Screen - ViewModel    
Domain: UseCase    
Data: Mapper - Repository

## Libraries choice

### UI

Compose + Navigation + Paging3 + Coil.    
I wanted to use Compose in a real context and experiment fragmentless implementation.

### Dependency injection

Koin is used because is easy to setup/use and supports Compose.

### Network

- OpenId AppAuth is used for the OAuth authentication flow.
- Apollo is used to consume GitHub GraphQL API.

### Testing

- Mockk because it's handy, powerful and simplify mock of suspending functions.
- Kluent for fluent assertion I like how it makes tests more readable.

### Others

- KotlinX DateTime is a multiplatform Kotlin date/time API, used to have a fully Kotlin domain layer which could be reused on another platform.
- Jetpack Security for `EncryptedSharedPreferences` to store securely API token.
- Gradle Kotlin DSL to make gradle great again.

## Challenges

- Learn GraphQL and deal with the GitHub GraphQL API which it's a tedious to query and doesn't provide filters mechanisms.
- Use Compose outside of a Codelabs with real constraints.
- Implement AppAuth in a clean architecture was tedious because strongly tied to some android components (e.g. Intent and Activity).

## Testing strategy

- Main focus on domain layer testing because our tests base should reflect our specifications.
- Test mapper on the data layer, test repository when it's make sense (e.g. test two data sources management behavior).
- Integration tests to test the application flow.

## UI/UX improvements

- Error handling, display error and provide a retry mechanism.
- Implement a real theme to replace the default generated one.
- UI for large screens.

## Architecture improvement

- Split into modules

## Completion

80%: missing error handling and fancy UI and navigation     
I estimate the remaining time of 2-3 days
