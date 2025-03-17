# Injeção de Dependências (`di`)

Esta pasta contém os módulos de injeção de dependências do projeto AutoMobile, configurados com o Hilt.

## Estrutura
- **`DatabaseModule.kt`**: Fornece dependências relacionadas ao banco de dados Room, como `AutoMobileDatabase` e os DAOs.
- **`FirebaseModule.kt`**: Fornece dependências relacionadas ao Firebase, como autenticação.
- **`NetworkModule.kt`**: (Futuro) Pode ser usado para fornecer dependências de rede (ex.: Retrofit, OkHttp).
- **`RepositoryModule.kt`**: Fornece as implementações dos repositórios (`AccidentRepository`, `DisplacementRepository`, etc.).

## Propósito
Os módulos nesta pasta são usados pelo Hilt para:
- Configurar e injetar dependências em ViewModels, repositórios e outros componentes.
- Garantir que o ciclo de vida das dependências seja gerenciado corretamente (ex.: singletons para o banco de dados).
- Abstrair a criação de objetos complexos, promovendo modularidade e testabilidade.

## Observações
- O Hilt é configurado no projeto via `@HiltAndroidApp` em `HiltApplication.kt`.
- Cada módulo usa anotações como `@Provides` e `@Singleton` para definir como as dependências devem ser fornecidas.
- Para adicionar novas dependências, crie um novo módulo ou modifique os existentes.