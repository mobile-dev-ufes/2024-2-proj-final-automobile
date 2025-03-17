# Camada de Interface do Usuário (`ui`)

Esta pasta contém a camada de interface do usuário do projeto AutoMobile, implementada com Jetpack Compose.

## Estrutura
- **`accident`**:
    - `AccidentScreen.kt`: Tela para registro de acidentes.
    - `AccidentViewModel.kt`: ViewModel para gerenciar o estado e a lógica da tela de acidentes.
- **`auth`**:
    - `AuthViewModel.kt`: ViewModel para autenticação de usuários.
    - `LoginScreen.kt`: Tela de login com Firebase Authentication.
- **`common`**:
    - `AccidentCard.kt`: Componente para exibir informações de um acidente.
    - `DatePicker.kt`: Componente para seleção de datas.
    - `MaintenanceCard.kt`: Componente para exibir informações de uma manutenção.
    - `ParseData.kt`: Utilitário para parsing de datas.
    - `VehicleCard.kt`: Componente para exibir informações de um veículo.
- **`dashboard`**:
    - `DashboardScreen.kt`: Tela principal do veículo, exibindo opções e informações.
    - `DashboardViewModel.kt`: ViewModel para a tela de dashboard.
- **`displacement`**:
    - `DisplacementScreen.kt`: Tela para registro de deslocamentos.
    - `DisplacementViewModel.kt`: ViewModel para a tela de deslocamentos.
- **`garage`**:
    - `GarageScreen.kt`: Tela para listar e gerenciar veículos.
    - `GarageViewModel.kt`: ViewModel para a tela de garagem.
- **`insurance`**:
    - `InsuranceScreen.kt`: Tela para registro de seguros.
    - `InsuranceViewModel.kt`: ViewModel para a tela de seguros.
- **`maintenance`**:
    - `MaintenanceReminderScreen.kt`: Tela para lembretes de manutenção.
    - `MaintenanceReminderViewModel.kt`: ViewModel para a tela de lembretes de manutenção.
    - `MaintenanceScreen.kt`: Tela para registro de manutenções.
    - `MaintenanceViewModel.kt`: ViewModel para a tela de manutenções.
- **`navigation`**:
    - `NavGraph.kt`: Configuração da navegação com Navigation Compose.
    - `NavRoutes.kt`: Definição das rotas de navegação.
- **`recharge`**:
    - `RechargeScreen.kt`: Tela para registro de reabastecimentos/cargas.
    - `RechargeViewModel.kt`: ViewModel para a tela de reabastecimentos/cargas.
- **`registry`**:
    - `RegistryScreen.kt`: Tela para registro de novos veículos.
    - `RegistryViewModel.kt`: ViewModel para a tela de registro.
- **`reports`**:
    - `ReportsScreen.kt`: Tela para exibir relatórios e estatísticas.
    - `ReportsViewModel.kt`: ViewModel para gerenciar os dados de relatórios.
- **`theme`**:
    - `Color.kt`: Definição das cores do tema.
    - `Theme.kt`: Configuração do tema da aplicação com Material Design.
    - `Type.kt`: Definição da tipografia do tema.

## Propósito
A camada de UI é responsável por:
- Implementar as telas do aplicativo usando Jetpack Compose.
- Gerenciar o estado e a lógica de cada tela com ViewModels.
- Fornecer componentes reutilizáveis (em `common`).
- Configurar a navegação entre telas (em `navigation`).
- Definir o tema visual da aplicação (em `theme`).

## Observações
- Todas as telas são implementadas como composables (`@Composable`).
- Os ViewModels são injetados com Hilt (anotados com `@HiltViewModel`).
- A internacionalização é suportada com `stringResource` para textos exibidos na UI.
- O tema é configurado em `theme` e segue as diretrizes do Material Design.