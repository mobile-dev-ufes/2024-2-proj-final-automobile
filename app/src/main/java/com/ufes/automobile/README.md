# AutoMobile - Gerenciamento de Veículos

Bem-vindo ao repositório do projeto **AutoMobile**, uma aplicação Android para gerenciamento de veículos. Este aplicativo permite que os usuários registrem e gerenciem informações sobre seus veículos, incluindo deslocamentos, reabastecimentos/cargas, manutenções, seguros, acidentes e relatórios de estatísticas. O projeto utiliza Jetpack Compose para a interface, Hilt para injeção de dependências, Room para persistência local, e Firebase para autenticação.

## Funcionalidades
- Registro e gerenciamento de veículos.
- Acompanhamento de deslocamentos, reabastecimentos/cargas, manutenções, seguros e acidentes.
- Relatórios com estatísticas de custos e distâncias percorridas.
- Lembretes de manutenção.
- Autenticação de usuários via Firebase.
- Suporte a internacionalização (strings.xml em múltiplos idiomas).

## Tecnologias Utilizadas
- **Linguagem**: Kotlin
- **UI**: Jetpack Compose
- **Injeção de Dependências**: Hilt
- **Persistência Local**: Room
- **Autenticação**: Firebase Authentication
- **Gráficos**: MPAndroidChart (para relatórios)
- **Navegação**: Navigation Compose

## Estrutura do Projeto
O projeto está organizado nas seguintes pastas principais:

- **`data`**: Contém a camada de dados (modelos, repositórios, DAOs, banco de dados Room, etc.).
- **`di`**: Módulos de injeção de dependências com Hilt.
- **`domain`**: Camada de domínio com modelos de negócio, repositórios abstratos e casos de uso.
- **`ui`**: Camada de interface do usuário, contendo telas, ViewModels e componentes de UI.
- **`HiltApplication.kt`**: Classe de aplicação principal configurada para Hilt.
- **`MainActivity.kt`**: Activity principal que inicializa o aplicativo.

## Pré-requisitos
- Android Studio (versão mais recente recomendada).
- Kotlin 2.1 ou superior.
- SDK Android API 33+ (mínimo).

## Como Configurar e Executar
1. **Clone o Repositório**:
   ```bash
   git clone https://github.com/mobile-dev-ufes/2024-2-proj-final-automobile.git

### 2. Abra no Android Studio
- Abra o projeto no Android Studio.
- Sincronize o projeto com o Gradle (Sync Project with Gradle Files).

### 3. Configure o Firebase
- Adicione seu arquivo `google-services.json` na pasta `app/` (obtenha-o no console do Firebase).
- Certifique-se de que a autenticação por e-mail está habilitada no Firebase.

### 4. Execute o Aplicativo
- Conecte um dispositivo Android ou use um emulador.
- Clique em `Run` no Android Studio.

## Vídeo mostrando o projeto
https://www.youtube.com/watch?v=2iSjAks51XA