# CONTEXTO COMPLETO DO PROJETO FREELADUO — CONTINUIDADE DO DESENVOLVIMENTO

Estou desenvolvendo um aplicativo Android chamado **FreelaDuo**, utilizando Android Studio, Java e XML. Quero que você assuma a continuidade desse projeto exatamente de onde parei.

Este documento contém o histórico, a estrutura, as funcionalidades implementadas, as decisões de desenvolvimento, os padrões visuais e as tarefas que ainda precisam ser realizadas.

Leia tudo antes de sugerir qualquer alteração. É muito importante preservar o que já foi desenvolvido.

---

# 1. INFORMAÇÕES GERAIS DO PROJETO

**Nome:** FreelaDuo

**Objetivo:** Criar uma plataforma que conecte freelancers e empresas, permitindo que profissionais encontrem oportunidades, divulguem seus projetos, participem de uma comunidade e construam sua presença profissional.

O aplicativo possui duas categorias principais de usuários:

- Freelancer.
- Empresa.

A proposta é oferecer um ambiente em que freelancers possam encontrar oportunidades de trabalho e empresas possam divulgar vagas e encontrar profissionais.

**Tecnologias utilizadas:**
- Android Studio.
- Java.
- XML para construção das interfaces.
- Retrofit para comunicação com a API.
- Gson para conversão de JSON.
- SharedPreferences para armazenamento local de algumas informações.
- Material Components para elementos visuais.
- Git e GitHub para versionamento.

**Pacote principal:**
`com.example.myapplication`

**Pasta do projeto no Windows:**
`C:\FreelaDuo_Desenvolvimento`

**Repositório GitHub:**
`https://github.com/Karenkaminsky/FreelaDuo.git`

**Branch principal:** `main`

---

# 2. IDENTIDADE VISUAL DO APLICATIVO

O FreelaDuo possui uma identidade visual moderna, com predominância de tons escuros, roxo e lilás.

A aparência deve transmitir profissionalismo, tecnologia e modernidade.

### Paleta de cores utilizada

- Fundo principal: `#070D17`
- Fundo secundário: `#0D192B`
- Fundo dos cards: `#111D30`
- Roxo principal: `#6A5ACD`
- Roxo/lilás de destaque: `#C084FC`
- Roxo complementar: `#8B5CF6`
- Amarelo/dourado Premium: `#FBBF24`
- Texto claro: branco e tons próximos.
- Texto secundário: azul acinzentado.

### Identidade dos planos

O aplicativo possui dois planos:

**Gratuito**
- Cor principal: lilás/roxo.
- Ícone: presente.
- Drawable: `ic_plan_gift`.
- Preço: R$ 0,00.

**Premium**
- Cor principal: dourado/amarelo.
- Ícone: diamante.
- Drawable: `outline_diamond_24`.
- Preço definido anteriormente: R$ 19,90 por mês.

Não modificar a identidade visual sem necessidade. Manter a combinação entre roxo, azul-escuro e dourado.

### Cabeçalho padrão

Foi criado um cabeçalho reutilizável:

`app/src/main/res/layout/view_header_freeladuo.xml`

Ele contém:
- Logo do aplicativo.
- Nome FreelaDuo.
- Identificação da tela atual.
- Estilo escuro com detalhes em roxo e lilás.

O cabeçalho apresenta uma identificação diferente em cada tela:

- `✦ FEED`
- `✦ COMUNIDADE`
- `✦ PUBLICAR`
- `✦ VAGAS`

A logo utilizada está em:

`app/src/main/res/drawable/logo.png`

---

# 3. ESTRUTURA DE NAVEGAÇÃO

O aplicativo possui uma navegação inferior fixa com cinco opções:

1. Início.
2. Comunidade.
3. Publicar.
4. Vagas.
5. Perfil.

Foi criado o arquivo:

`app/src/main/res/menu/bottom_nav_menu.xml`

Também foi desenvolvido:

`app/src/main/java/com/example/myapplication/BottomNavigationHelper.java`

Cada Activity configura a opção selecionada por meio de:

- Feed: `BottomNavigationHelper.configurar(this, R.id.nav_home);`
- Comunidade: `BottomNavigationHelper.configurar(this, R.id.nav_network);`
- Publicar: `BottomNavigationHelper.configurar(this, R.id.nav_post);`
- Vagas: `BottomNavigationHelper.configurar(this, R.id.nav_jobs);`
- Perfil: `BottomNavigationHelper.configurar(this, R.id.nav_profile);`

**ATENÇÃO:** Os ícones da navegação inferior já foram escolhidos e a usuária não quer que sejam modificados. Não trocar os ícones nem alterar a estrutura da navegação sem autorização.

---

# 4. TELAS DESENVOLVIDAS

## 4.1. Tela de login

**Activity:**
`register/MainActivity.java`

É a tela inicial de autenticação do aplicativo.

Possui:
- Campo de e-mail.
- Campo de senha.
- Botão de login.
- Opção de recuperação de senha.
- Acesso ao cadastro.
- Botão de login com Google.
- Botão de login com GitHub.
- Botão de login com LinkedIn.

### Login tradicional

O login por e-mail e senha já possui integração com a API por meio do Retrofit.

O endpoint utilizado é:

`POST login.php`

Quando o login é realizado com sucesso, são armazenadas informações da sessão utilizando SharedPreferences, incluindo:

- `isLoggedIn`
- `user_id`
- `tipo_conta`
- `nome`

Após o login, o usuário é direcionado para `FeedActivity`.

### Login social

Os botões Google, GitHub e LinkedIn já estão presentes na tela.

Porém, atualmente, eles ainda exibem mensagens informando que a integração está em desenvolvimento.

**IMPORTANTE: Uma das próximas tarefas é implementar a autenticação REAL com Google, GitHub e LinkedIn.**

A usuária já deixou claro que não quer apenas abrir os sites dos provedores ou mostrar mensagens. Quer que os botões realmente permitam entrar na conta utilizando esses serviços.

A implementação deverá considerar OAuth, credenciais dos provedores, callbacks, validação de tokens e integração com o backend, conforme a arquitetura escolhida.

Não afirmar que o login social está implementado enquanto não houver autenticação real.

---

## 4.2. Tela de recuperação de senha

**Activity:**
`register/SenhaRecup.java`

**Layout:**
`app/src/main/res/layout/senharecup.xml`

A tela possui três etapas visuais:

1. Informar o e-mail.
2. Exibir uma confirmação visual de envio.
3. Informar um código de seis dígitos.

A interface foi construída com um diálogo personalizado.

Atualmente, o fluxo é demonstrativo:
- Não envia e-mail de verdade.
- A validação verifica apenas se o código possui seis dígitos.
- Após a validação, exibe uma mensagem informando que o código foi validado e que a senha pode ser redefinida.

**Pendente:** Integrar a recuperação de senha com o backend, incluindo envio real do código, validação e redefinição de senha.

---

## 4.3. Tela de cadastro

Existem telas separadas para direcionar e realizar o cadastro.

Arquivos/Activities identificados no projeto:

- `RegisterSelectionActivity`
- `RegisterCompanyActivity`
- `register/RegisterFreelancerActivity`
- `register/RegisterActivity`

O aplicativo possui distinção entre cadastro de freelancer e cadastro de empresa.

A implementação do cadastro tradicional já possui uma chamada Retrofit:

`POST cadastro.php`

O método está declarado em `ApiService.java`.

É necessário consultar os arquivos atuais antes de modificar qualquer funcionalidade de cadastro.

---

## 4.4. Tela inicial — Feed

**Activity:**
`FeedActivity.java`

É a tela principal do aplicativo, acessada após o login.

Possui:
- Cabeçalho FreelaDuo.
- Identificação `✦ FEED`.
- Navegação inferior.
- Estrutura visual de feed.

A tela funciona como ponto central de acesso às demais áreas do aplicativo.

Preservar a estrutura existente e consultar os arquivos atuais antes de adicionar funcionalidades.

---

## 4.5. Tela Comunidade

**Activity:**
`Comunidade.java`

**Layout:**
`activity_comunidade.xml`

A tela representa uma comunidade em que os usuários podem visualizar conversas e oportunidades.

Possui:
- Cabeçalho com identificação `✦ COMUNIDADE`.
- Título e descrição.
- Área dinâmica de conteúdo.
- Navegação inferior fixa.
- Abas de canais.

### Canais existentes

- `#geral`
- `#oportunidades`
- `#projetos`
- `#duvidas`

### Conteúdo demonstrativo

Foram criadas mensagens de exemplo utilizando fotos de colegas como avatares.

Também foram adicionados cards demonstrativos de oportunidades de empresas:

**Nubank**
- Vaga: Front-end Jr.
- Tecnologias: React e JavaScript.

**Itaú**
- Vaga: Back-end Jr.
- Tecnologias: Java, Spring Boot e SQL.

**TOTVS**
- Vaga: Full Stack Jr.
- Tecnologias: C#, .NET e JavaScript.

Os links dessas oportunidades são demonstrativos, utilizando endereços `.example`.

A tela atualmente utiliza conteúdo local de demonstração. Não possui comunicação em tempo real nem persistência de mensagens no backend.

**Pendente:** Futuramente, integrar a comunidade com a API, caso seja necessário implementar mensagens reais, armazenamento e interação entre usuários.

A usuária gostou do visual atual e não deseja adicionar elementos desnecessários.

---

## 4.6. Tela Publicar

**Activity:**
`Publicar.java`

Possui:
- Cabeçalho com identificação `✦ PUBLICAR`.
- Navegação inferior.
- Estrutura destinada à publicação de conteúdo.

É uma das áreas principais do aplicativo, voltada à divulgação de projetos, serviços ou conteúdos dos usuários.

Antes de alterar essa tela, analisar sua implementação atual e preservar o que já existe.

---

## 4.7. Tela Vagas

**Activity:**
`Vagas.java`

**Layout:**
`activity_vagas.xml`

Essa tela apresenta oportunidades de trabalho para os usuários.

Possui:
- Cabeçalho com identificação `✦ VAGAS`.
- Título e descrição.
- Campo de pesquisa.
- Filtros horizontais.
- Lista dinâmica de vagas.
- Navegação inferior fixa.

### Elementos identificados no XML

- `edtPesquisarVaga`: campo de pesquisa.
- `scrollFiltrosVagas`: rolagem horizontal dos filtros.
- `containerFiltrosVagas`: opções de filtros.
- `listaVagas`: área em que as vagas são inseridas dinamicamente.

### Vagas demonstrativas já criadas

1. TOTVS — Full Stack.
2. Nubank — Front-end.
3. Itaú — Back-end.
4. Tech Solutions — Android.
5. Quality Tech — QA.
6. Creative Studio — UI/UX.
7. NVIDIA — Full Stack com foco em Back-end.

Os cards apresentam informações como:
- Nome da empresa.
- Logo da empresa.
- Nome da vaga.
- Descrição.
- Informações da oportunidade.
- Pessoa que compartilhou a vaga.
- Foto de quem compartilhou.

### Imagens utilizadas

- TOTVS: `logo_totvs.jpg`
- Nubank: `logo_nubank.webp`
- Itaú: `logp_itau.png`
- Tech Solutions: `empresatech.png`
- Quality Tech: `logo.png`
- NVIDIA: `nvidia.png`
- Fotos dos usuários: imagens de colegas e `perfilkaren.jpeg`.

A vaga da NVIDIA é um exemplo demonstrativo, não uma vaga oficial confirmada da empresa.

**Pendente:** Futuramente, integrar a listagem de vagas com o backend, permitindo buscar, cadastrar, filtrar e visualizar oportunidades reais.

---

## 4.8. Tela Perfil

**Activity:**
`ProfileActivity.java`

**Layout:**
`app/src/main/res/layout/activity_profile.xml`

É a tela de perfil do usuário.

Possui:
- Foto de perfil.
- Nome.
- Título profissional.
- Biografia.
- Botão para alterar foto.
- Botão para salvar perfil.
- Botão de sair.
- Botão de excluir conta.
- Identificação do tipo de usuário.
- Card que apresenta o plano atual.
- Botão para visualizar os planos.
- Navegação inferior.

### IDs importantes

- `imgProfile`
- `btnChangePhoto`
- `edtName`
- `edtTitle`
- `edtBio`
- `btnSaveProfile`
- `btnLogout`
- `btnDeleteAccount`
- `cardPlan`
- `txtPlanLabel`
- `txtPlanBadge`
- `containerPlanIcon`
- `imgCurrentPlanIcon`
- `txtCurrentPlan`
- `btnViewPlans`

### Foto de perfil

A imagem demonstrativa utilizada é:

`perfilkaren.jpeg`

### Funcionamento do card de plano

Foi implementada uma atualização dinâmica conforme o plano salvo no SharedPreferences.

SharedPreferences:
- Nome: `FreelaDuoPlanos`
- Chave: `plano_atual`

Valores utilizados:
- `gratuito`
- `premium`

Quando o usuário está no plano Gratuito:
- Exibe o ícone de presente.
- Utiliza tons de roxo e lilás.
- Apresenta a identificação Plano Gratuito.
- Aplica o estilo correspondente ao plano.

Quando o usuário está no Premium:
- Exibe o ícone de diamante.
- Utiliza detalhes dourados/amarelos.
- Apresenta a identificação Plano Premium.
- Atualiza o card, o selo, o ícone e o botão com as cores correspondentes.

A atualização é chamada no `onResume()`, permitindo que o perfil atualize o plano depois de voltar da tela de planos.

### Observações importantes

Algumas funções ainda são demonstrativas:
- Salvar perfil atualmente apresenta uma mensagem, sem persistência real no backend.
- Excluir conta ainda não realiza a exclusão efetiva no servidor.
- Logout limpa os dados da sessão `FreelaDuoPrefs`.
- As informações de plano são mantidas separadamente em `FreelaDuoPlanos`.

Não alterar essas funcionalidades sem analisar o código atual e entender o comportamento esperado.

---

## 4.9. Tela de Planos

**Activity:**
`PlanosActivity.java`

**Layout:**
`app/src/main/res/layout/activity_planos.xml`

Essa tela apresenta as opções de assinatura do FreelaDuo.

Possui dois cards:

### Plano Gratuito

- Preço: R$ 0,00.
- Ícone de presente.
- Cor roxa/lilás.
- Acesso à comunidade.
- Visualização de oportunidades.
- Perfil de freelancer.

### Plano Premium

- Preço definido anteriormente: R$ 19,90 por mês.
- Ícone de diamante.
- Cor dourada.
- Recursos do plano gratuito.
- Destaque no perfil.
- Recursos exclusivos.
- Maior visibilidade.

### IDs importantes

- `btnBackPlanos`
- `btnPlanoGratuito`
- `btnEscolherPremium`

### Seleção de plano

A seleção atualmente é simulada localmente.

Utiliza:
- SharedPreferences: `FreelaDuoPlanos`
- Chave: `plano_atual`

O valor salvo é:
- `gratuito`
- `premium`

A tela permite alternar entre os planos e atualiza os botões conforme a seleção.

**IMPORTANTE:** Ainda não existe pagamento real, assinatura recorrente ou integração com gateway de pagamento.

O plano Premium é apenas uma seleção local demonstrativa.

A usuária deseja manter inicialmente apenas dois planos: Gratuito e Premium.

Não inventar novos planos ou alterar os preços sem autorização.

---

# 5. API E BACKEND

O projeto já possui uma estrutura de integração com backend utilizando Retrofit.

Arquivos:

- `ApiClient.java`
- `ApiService.java`
- `LoginResponse.java`
- `Projeto.java`

### ApiClient.java

Local:

`app/src/main/java/com/example/myapplication/ApiClient.java`

Configuração atual:

- Retrofit.
- GsonConverterFactory.
- URL base:

`http://192.168.1.145:8000/`

Métodos disponíveis:
- `getClient()`
- `getApiService()`

A URL utiliza um IP de rede local. Portanto, pode ser necessário ajustá-la quando o ambiente de desenvolvimento mudar ou quando o backend for publicado em um servidor acessível externamente.

Não trocar a URL sem verificar o ambiente em que o backend está sendo executado.

### ApiService.java

Local:

`app/src/main/java/com/example/myapplication/ApiService.java`

Endpoints declarados:

**Login**
`POST login.php`

Método:
`realizarLogin(@Body LoginRequestDto)`

**Cadastro**
`POST cadastro.php`

Método:
`cadastrarUsuario(@Body RegisterRequestDto)`

**Exclusão de usuário**
`POST deletar_usuario.php`

Método:
`deletarUsuario(@Body DeletarUsuarioDto)`

**Listagem de projetos**
`GET projetos.php`

Método:
`listarProjetos()`

**Criação de projetos**
`POST projetos.php`

Método:
`criarProjeto(@Body Projeto)`

### LoginResponse.java

Contém campos relacionados à resposta da autenticação:

- `status`
- `message`
- `id_usuario`
- `nome`
- `email`
- `tipo_conta`

Possui getters para acessar as informações.

### Projeto.java

Representa um projeto cadastrado.

Campos:
- `id_projeto`
- `id_usuario`
- `titulo`
- `descricao`
- `categoria`
- `orcamento`
- `status`
- `data_publicacao`

Possui getters para título, descrição, categoria e orçamento.

---

# 6. ARMAZENAMENTO LOCAL E SESSÃO

O aplicativo utiliza SharedPreferences.

### FreelaDuoPrefs

Utilizado para informações de autenticação e sessão.

Informações já identificadas:
- `isLoggedIn`
- `user_id`
- `tipo_conta`
- `nome`

### FreelaDuoPlanos

Utilizado para armazenar o plano selecionado.

Chave:
`plano_atual`

Valores:
- `gratuito`
- `premium`

Manter separadas as informações de sessão e as informações de plano, respeitando a lógica atual.

---

# 7. ANDROIDMANIFEST.XML

O Manifest já foi organizado para evitar declarações duplicadas de Activities.

Activities existentes:

- `.register.MainActivity`
- `.FeedActivity`
- `.ProfileActivity`
- `.PlanosActivity`
- `.Comunidade`
- `.Publicar`
- `.Vagas`
- `.RegisterSelectionActivity`
- `.RegisterCompanyActivity`
- `.register.RegisterFreelancerActivity`
- `.register.RegisterActivity`

A Activity inicial é:

`.register.MainActivity`

Ela está configurada como launcher e `exported="true"`.

A Activity `.PlanosActivity` foi adicionada com `exported="false"`.

Sempre verificar o Manifest antes de adicionar uma nova Activity, evitando duplicações.

---

# 8. ARQUIVOS E RECURSOS VISUAIS

### Imagens em drawable

As imagens existentes incluem:

- `carol.jpeg`
- `joao.jpeg`
- `lucas.jpeg`
- `miguel.jpeg`
- `allan.jpeg`
- `pedro.jpeg`
- `logo_nubank.webp`
- `logp_itau.png`
- `logo_totvs.jpg`
- `empresatech.png`
- `nvidia.png`
- `logo.png`
- `perfilkaren.jpeg`

### Ícones

- `ic_plan_gift.xml`: ícone do plano Gratuito.
- `outline_diamond_24.xml`: ícone do plano Premium.

Não recriar recursos que já existem sem verificar a pasta drawable.

Respeitar os nomes exatos dos arquivos. Por exemplo, o arquivo do Itaú é `logp_itau.png`, e não `logo_itau.png`.

---

# 9. GIT E VERSIONAMENTO

O projeto está no GitHub:

`https://github.com/Karenkaminsky/FreelaDuo.git`

Branch principal:
`main`

A usuária utiliza Git pelo terminal do PowerShell no Windows.

### Preferência de trabalho

Sempre que uma funcionalidade estiver concluída e compilando, sugerir um commit.

Não realizar commits ou pushes sem autorização explícita.

Antes de orientar um commit, solicitar ou analisar o resultado de:

`git status`

Adicionar somente os arquivos relacionados à alteração.

Exemplo:

`git add app/src/main/java/com/example/myapplication/ProfileActivity.java`

Depois verificar novamente:

`git status`

E então sugerir uma mensagem de commit.

### Padrão das mensagens

Utilizar Conventional Commits em inglês.

Exemplos já utilizados ou sugeridos:

`feat: add subscription plans screen`

`feat: connect profile to subscription plans screen`

`feat: add local subscription plan selection`

`feat: update profile plan icon and theme dynamically`

`feat: add sample job opportunities to community`

`feat: update job listings with company and user images`

Explicar em português o significado da mensagem quando sugerir um commit.

### Arquivos da pasta .idea

Os arquivos:
- `.idea/compiler.xml`
- `.idea/misc.xml`

Foram incluídos em um commit realizado anteriormente na máquina de casa da usuária.

O projeto utiliza JDK 21.

Não presumir que esses arquivos estejam sempre sem versionamento. Conferir o Git antes de orientar alterações.

Evitar adicionar mudanças específicas da máquina sem necessidade.

---

# 10. FUNCIONALIDADES QUE JÁ EXISTEM E O QUE AINDA É DEMONSTRATIVO

É importante diferenciar o que já funciona localmente do que ainda depende de integração.

| Funcionalidade | Situação |
|---|---|
| Login tradicional | Integrado ao endpoint da API |
| Armazenamento de sessão | Implementado localmente |
| Login Google | Botão presente, integração pendente |
| Login GitHub | Botão presente, integração pendente |
| Login LinkedIn | Botão presente, integração pendente |
| Recuperação de senha | Interface demonstrativa |
| Cadastro | Chamada Retrofit declarada; verificar funcionamento completo |
| Feed | Tela criada |
| Comunidade | Conteúdo demonstrativo local |
| Publicar | Tela criada; verificar funcionalidades atuais |
| Vagas | Listagem demonstrativa local |
| Perfil | Tela criada |
| Alteração de foto | Implementada na interface |
| Salvar perfil | Ainda demonstrativo |
| Exclusão de conta | Ainda demonstrativa na interface |
| Tela de planos | Implementada |
| Seleção de plano | Funciona localmente |
| Pagamento Premium | Pendente |
| Persistência real de assinatura | Pendente |
| Integração da comunidade com backend | Pendente |
| Integração das vagas com backend | Pendente |

---

# 11. PRÓXIMAS ETAPAS DO DESENVOLVIMENTO

Uma das prioridades já definidas é implementar os logins sociais reais.

### Prioridade 1 — Autenticação social

Implementar:
1. Login com Google.
2. Login com GitHub.
3. Login com LinkedIn.

A implementação precisa:
- Autenticar de verdade com os provedores.
- Utilizar credenciais e configurações apropriadas.
- Validar os tokens.
- Integrar o resultado com o backend.
- Criar ou localizar o usuário no banco.
- Criar a sessão do aplicativo.
- Redirecionar corretamente para o FeedActivity.

Não implementar soluções falsas, como simplesmente abrir o navegador ou exibir uma mensagem de sucesso.

### Prioridade 2 — Recuperação real de senha

- Envio de código por e-mail.
- Validação do código no backend.
- Redefinição da senha.
- Tratamento de erros e expiração do código.

### Prioridade 3 — Persistência do perfil

- Salvar nome.
- Salvar título profissional.
- Salvar biografia.
- Salvar foto.
- Carregar os dados do usuário a partir da API.

### Prioridade 4 — Integração das vagas

- Listar oportunidades reais.
- Buscar vagas.
- Aplicar filtros.
- Exibir detalhes.
- Integrar com o backend.

### Prioridade 5 — Integração da comunidade

- Buscar mensagens.
- Enviar mensagens.
- Persistir conversas.
- Organizar canais.
- Identificar os usuários.

### Prioridade 6 — Publicação de projetos e conteúdos

- Criar publicações.
- Salvar no backend.
- Listar publicações.
- Exibir informações do autor.

### Prioridade 7 — Planos e pagamentos

- Definir como o Premium será contratado.
- Integrar um meio de pagamento.
- Validar pagamentos no backend.
- Persistir a assinatura.
- Atualizar o plano do usuário com base no status real da assinatura.

### Prioridade 8 — Segurança e finalização

- Validar sessões.
- Proteger endpoints.
- Implementar exclusão real de conta.
- Tratar erros de conexão.
- Revisar permissões.
- Preparar o aplicativo para testes e publicação.

Essas etapas são prioridades futuras, não funcionalidades que já estejam concluídas.

---

# 12. COMO VOCÊ DEVE TRABALHAR COMIGO

Estas orientações são muito importantes.

### 1. Trabalhe uma alteração por vez

Não quero receber várias alterações diferentes de uma só vez.

Explique o próximo passo, aguarde minha execução e só depois continue.

### 2. Sempre informe o caminho completo do arquivo

Por exemplo:

`app/src/main/java/com/example/myapplication/ProfileActivity.java`

Não diga apenas para alterar uma classe sem informar onde ela está.

### 3. Quando eu enviar um código, analise o código inteiro

Não faça alterações precipitadas.

Verifique:
- O que já existe.
- Quais funcionalidades precisam ser preservadas.
- Quais IDs são utilizados.
- Quais dependências existem.
- Se há alguma integração com outros arquivos.

### 4. Quando for necessário substituir um código, envie o código completo

Prefiro receber o arquivo completo e corrigido, em vez de pequenos fragmentos que podem ser inseridos no local errado.

Isso vale principalmente para:
- Java.
- XML.
- AndroidManifest.xml.
- Arquivos de configuração.

Não remova funcionalidades existentes ao fornecer uma versão completa.

### 5. Não invente nomes de arquivos, IDs ou métodos

Se não tiver certeza de algum nome, solicite que eu envie o arquivo atual.

Não presuma que a estrutura esteja exatamente como você imagina.

### 6. Explique onde devo executar cada comando

Estou utilizando Windows e PowerShell.

Sempre deixe claro:
- Se o comando deve ser executado no terminal do Android Studio ou no PowerShell.
- Em qual pasta devo estar.
- Qual arquivo devo abrir.
- O que devo substituir.

### 7. Preserve o visual existente

Não altere:
- Paleta de cores sem necessidade.
- Ícones da navegação inferior.
- Logo.
- Estrutura dos cabeçalhos.
- Identidade visual dos planos.
- Elementos que já foram aprovados.

Se houver uma melhoria visual, explique antes e aguarde minha aprovação quando ela mudar significativamente o que já existe.

### 8. Não peça testes no celular

Atualmente, não consigo realizar testes pelo celular.

Prefira orientar a validação pelo Android Studio, principalmente:
- Build > Make Project.
- Verificação de erros de compilação.
- Análise de mensagens do Gradle.
- Inspeção do código.

Não confunda compilação bem-sucedida com funcionalidade já validada em execução.

### 9. Não faça commit ou push por conta própria

Você pode sugerir commits quando uma etapa estiver pronta, mas não deve executar ou presumir que foram realizados.

### 10. Não repita etapas concluídas

Antes de propor uma alteração, considere todo o histórico deste documento e pergunte somente quando faltar uma informação realmente necessária.

### 11. Seja objetivo

Explique de maneira clara, sem excesso de informações técnicas desnecessárias.

Quero aprender durante o desenvolvimento, mas também preciso conseguir executar cada etapa sem ficar confusa.

### 12. Preserve a arquitetura existente

Antes de adicionar bibliotecas, alterar dependências, modificar o Manifest ou criar novas classes, verifique o que o projeto já possui.

Evite duplicar funcionalidades.

---

# 13. ESTADO ATUAL E CONTINUIDADE

O desenvolvimento já passou pela criação de diversas telas, estrutura visual, navegação, integração inicial com API, armazenamento local e seleção de planos.

O projeto já possui uma base funcional e não deve ser tratado como um aplicativo começando do zero.

A implementação mais recente envolveu a atualização dinâmica do card de plano no perfil:
- Gratuito com presente e tons roxos.
- Premium com diamante e tons dourados.

A usuária informou que o projeto compilou corretamente após essa alteração.

Também foi realizado anteriormente um commit na máquina de casa contendo arquivos de configuração do Android Studio e classes relacionadas à API e autenticação.

A autenticação tradicional já utiliza Retrofit, enquanto Google, GitHub e LinkedIn ainda precisam ser integrados de verdade.

**IMPORTANTE: Antes de continuar, não saia modificando arquivos imediatamente.**

Primeiro:
1. Leia e considere todo este contexto.
2. Reconheça que o projeto já está em andamento.
3. Pergunte qual arquivo ou funcionalidade vamos continuar.
4. Se a tarefa depender do estado atual do código, solicite os arquivos necessários.
5. Continue exatamente do ponto em que paramos, uma etapa por vez.

Quero que você seja responsável por dar continuidade ao desenvolvimento do FreelaDuo, mantendo o padrão visual, a arquitetura e as decisões que já foram tomadas.

**Nosso objetivo é concluir o aplicativo com funcionalidades reais, integrações com backend e uma experiência visual profissional, sem perder o trabalho que já foi feito.**

# 14. BACKUP E SEGURANÇA DO PROJETO

Em 23/11/2026, realizamos um procedimento de backup do aplicativo FreelaDuo utilizando ADB (Android Debug Bridge).

### Backup realizado

Foi utilizado o comando:

`adb backup -f ".\backup-freeladuo-confirmado.ab" -noapk com.example.myapplication`

Informações do arquivo:

- **Nome:** `backup-freeladuo-confirmado.ab`
- **Local:** `C:\FreelaDuo_Teste\backup-freeladuo-confirmado.ab`
- **Data:** 23/11/2026, às 09:26:28.
- **Tamanho:** 1.310 bytes.
- **Pacote:** `com.example.myapplication`
- **Opção utilizada:** `-noapk`.

A operação foi concluída e o PowerShell retornou ao prompt normalmente.

### Primeira tentativa

Também houve uma primeira tentativa, que gerou:

`C:\FreelaDuo_Teste\backup-freeladuo.ab`

Essa tentativa foi interrompida acidentalmente com Ctrl+C, embora a autorização do backup já tivesse sido confirmada no celular.

Por isso, foi realizada uma segunda tentativa, gerando o arquivo `backup-freeladuo-confirmado.ab`.

### Atenção sobre a confiabilidade do backup

O comando `adb backup` utilizado é um recurso antigo/depreciado do Android.

O arquivo foi gerado, mas seu tamanho de 1.310 bytes não comprova que todos os dados internos do aplicativo foram efetivamente incluídos.

Portanto:
- Não considerar esse arquivo como garantia de recuperação completa do aplicativo.
- Não apagar ou desinstalar o aplicativo antigo com base apenas nesse backup.
- Preservar o arquivo original.
- Não sobrescrever o backup sem autorização.
- Antes de alterações importantes, verificar se existe uma cópia completa e recuperável do projeto.

Também foi sugerido realizar uma cópia da pasta do projeto para `C:\FreelaDuo_Teste`, utilizando `robocopy`, excluindo pastas temporárias como `.gradle`, `build` e `.git`. Porém, não há confirmação de que essa segunda cópia da pasta do projeto tenha sido realizada.

### Orientação para a continuidade

Antes de qualquer alteração estrutural importante, avaliar a necessidade de criar um backup atualizado do projeto.

Nunca presumir que o backup antigo garante a recuperação de todos os arquivos, configurações, dados ou informações de usuários.