# Camada de Domínio (`domain`)

Esta pasta contém a camada de domínio do projeto AutoMobile, que define a lógica de negócio e os contratos para acesso a dados.

## Estrutura
- **`model`**:
    - `Vehicle.kt`: Modelo de domínio que representa um veículo na aplicação.
- **`repository`**: Interfaces abstratas dos repositórios, definindo os contratos para acesso a dados.
    - `AccidentRepository.kt`: Contrato para operações com acidentes.
    - `DisplacementRepository.kt`: Contrato para operações com deslocamentos.
    - `GarageRepository.kt`: Contrato para operações com veículos.
    - `InsuranceRepository.kt`: Contrato para operações com seguros.
    - `MaintenanceReminderRepository.kt`: Contrato para operações com lembretes de manutenção.
    - `MaintenanceRepository.kt`: Contrato para operações com manutenções.
    - `RechargeRepository.kt`: Contrato para operações com reabastecimentos/cargas.
- **`usecase`**: (Vazio) Pode ser usado para implementar casos de uso (use cases) que encapsulam lógica de negócio.

## Propósito
A camada de domínio é responsável por:
- Definir os modelos de negócio principais (ex.: `Vehicle`).
- Estabelecer contratos para acesso a dados via interfaces de repositórios.
- (Futuro) Implementar casos de uso que orquestram a lógica de negócio.

## Observações
- Os modelos de domínio (ex.: `Vehicle`) são independentes da camada de dados e da UI, promovendo uma arquitetura limpa.
- As interfaces de repositórios abstraem as fontes de dados, permitindo que as implementações (em `data/repository`) sejam trocadas sem afetar o resto da aplicação.
- A pasta `usecase` pode ser usada para adicionar lógica de negócio complexa, como validações ou cálculos.