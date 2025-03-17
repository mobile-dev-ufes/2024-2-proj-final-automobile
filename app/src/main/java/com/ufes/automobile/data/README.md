
# Camada de Dados (`data`)

Esta pasta contém a camada de dados do projeto AutoMobile, responsável por gerenciar o acesso e a manipulação de dados. Aqui estão implementados os repositórios, DAOs, entidades do banco de dados Room e eventuais integrações com APIs remotas.

## Estrutura
- **`local`**:
  - **`dao`**: Contém as interfaces DAO (Data Access Object) para interação com o banco de dados Room.
    - `AccidentDao.kt`: DAO para acidentes.
    - `DisplacementDao.kt`: DAO para deslocamentos.
    - `InsuranceDao.kt`: DAO para seguros.
    - `MaintenanceDao.kt`: DAO para manutenções.
    - `MaintenanceReminderDao.kt`: DAO para lembretes de manutenção.
    - `RechargeDao.kt`: DAO para reabastecimentos/cargas.
    - `VehicleDao.kt`: DAO para veículos.
  - **`database`**: Configuração do banco de dados Room.
    - `AutoMobileDatabase.kt`: Define o banco de dados e suas entidades.
  - **`entity`**: Entidades do Room que representam as tabelas do banco de dados.
    - `AccidentEntity.kt`: Entidade para acidentes.
    - `DisplacementEntity.kt`: Entidade para deslocamentos.
    - `InsuranceEntity.kt`: Entidade para seguros.
    - `MaintenanceEntity.kt`: Entidade para manutenções.
    - `MaintenanceReminderEntity.kt`: Entidade para lembretes de manutenção.
    - `RechargeEntity.kt`: Entidade para reabastecimentos/cargas.
    - `VehicleEntity.kt`: Entidade para veículos.
- **`model`**: (Vazio) Pode ser usado para modelos de dados intermediários ou DTOs (Data Transfer Objects).
- **`remote`**: (Vazio) Pode ser usado para integrações com APIs remotas (ex.: Retrofit).
- **`repository`**: Implementações concretas dos repositórios definidos na camada de domínio.
  - `AccidentRepositoryImpl.kt`: Implementação do repositório de acidentes.
  - `DisplacementRepositoryImpl.kt`: Implementação do repositório de deslocamentos.
  - `GarageRepositoryImpl.kt`: Implementação do repositório de veículos.
  - `InsuranceRepositoryImpl.kt`: Implementação do repositório de seguros.
  - `MaintenanceReminderRepositoryImpl.kt`: Implementação do repositório de lembretes de manutenção.
  - `MaintenanceRepositoryImpl.kt`: Implementação do repositório de manutenções.
  - `RechargeRepositoryImpl.kt`: Implementação do repositório de reabastecimentos/cargas.

## Propósito
A camada de dados é responsável por:
- Gerenciar o acesso ao banco de dados local via Room.
- Fornecer implementações dos repositórios que abstraem as fontes de dados (local ou remota).
- Mapear entidades do banco de dados para modelos de domínio (quando necessário).

## Observações
- As entidades (`entity`) são específicas do Room e refletem a estrutura do banco de dados.
- Os DAOs (`dao`) definem operações de CRUD (Create, Read, Update, Delete) para cada entidade.
- Os repositórios (`repository`) são usados pela camada de domínio para acessar dados, abstraindo a fonte de dados (local ou remota).