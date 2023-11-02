# Package Structure

Within our project's root package, files packages are grouped by feature. The only exception is the
core module with classes that are shared across the app. Within a feature package, information is
split by responsibility. The ui code will go into one package, while the domain information in 
another - which is then split by use cases, repositories and model classes.

```
|-- onepercentbetter
|-- MainActivity.kt
|-- OnePercentBetterApp.kt
|-- core
|   |-- data
|   |   |-- Result.kt
|   |-- di
|   |   |-- RepositoryModule.kt
|   |   |-- UseCaseModule.kt
|   |-- ui
|       |-- components
|       |   |-- PrimaryButton.kt
|       |   |-- SecondaryButton.kt
|       |   |-- TOATextField.kt
|       |   |-- UIText.kt
|       |   |-- VerticalSpacer.kt
|       |-- theme
|           |-- Colors.kt
|           |-- Shape.kt
|           |-- Theme.kt
|           |-- Type.kt
|-- login
|-- domain
|   |-- model
|   |   |-- CredentialsModel.kt
|   |   |-- InvalidCredentialsException.kt
|   |   |-- LoginResponse.kt
|   |   |-- LoginResult.kt
|   |   |-- TokenModel.kt
|   |-- repository
|   |   |-- DemoLoginRepositoryImpl.kt
|   |   |-- DemoTokenRepositoryImpl.kt
|   |   |-- LoginRepository.kt
|   |   |-- TokenRepository.kt
|   |-- usecase
|       |-- CredentialsLoginUseCase.kt
|       |-- DemoCredentialsLoginUseCase.kt
|       |-- ProdCredentialsLoginUseCase.kt
|-- ui
|-- LoginContent.kt
|-- LoginScreen.kt
|-- LoginViewModel.kt
|-- LoginViewState.kt
```