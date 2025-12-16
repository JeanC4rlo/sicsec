# SICSEC - Sistema de Integração de Serviços Essenciais do CEFET
O produto SICSEC 1.0 visa integrar os sistemas acadêmico (SIGAA), bibliotecário (SophiA) e a Identificação Única, de forma de facilitar a vida de alunos e professores do CEFET-MG ao fornecer um acesso unificado a tais funcionalidades.

## Equipe de Desenvolvimento
| **Ordem** | **Nome** | 
| - | ------------------------------- |
| 1 | César Costa Ribeiro             |
| 2 | Davi Guilherme Soares Freitas   |
| 3 | Jean Carlo da Silva Santos      |
| 4 | Luiza Marques Silva             |
| 5 | Wesley Samuel Rodrigues Batista |

## Atores do Sistema
| Ator         | Definição                                                                                                                                                                                                                                                                                            |
|--------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Usuário      | Usuário abstrato, de onde outros tipos de usuário descendem (Aluno, Professor, Pesquisador, Administrador).                                                                                                                                                                                           |
| Aluno        | Usuário com o cargo de aluno. Usa o produto para acessar: sistema acadêmico (atividades, materiais de disciplinas, boletim, matrícula, bolsas, certificações) e sistema bibliotecário (empréstimos, reservas, acervo).                                       |
| Professor    | Usuário com o cargo de professor. Usa o produto para acessar: sistema acadêmico (atividades, materiais, notícias, lista de presença, consulta de alunos, bolsas, certificações) e sistema bibliotecário (empréstimos, reservas, acervo).                       |
| Pesquisador  | Usuário com cargo de pesquisador, alheio à instituição. Possui acesso limitado a alguns serviços, como à biblioteca, para realizar pesquisas.                                                                                                                  |
| Administrador| Usuário no sistema com permissão para realizar todas as funções que exigem maior cautela ou que não podem ser de domínio público.                                                                                                                             |
| Bibliotecário| Usuário no sistema com permissão para gerenciar a biblioteca: livros, empréstimos e reservas.                                                                                                                                                                  |

## Requisitos funcionais

| ID Requisito | Ator(es) | Funcionalidade | Descrição |
|--------------|----------|----------------|-----------|
| **REQ01** | Administrador | Cadastrar Matrícula | O sistema deve permitir que o administrador cuide da matrícula de professores, alunos e outros administradores. |
| **REQ02** | Administrador, Professor | Consultar Matrícula | O sistema deve permitir que o administrador possa consultar de um aluno: alguns dados cadastrais como nome, e-mail, telefone e CPF; dados bancários; histórico acadêmico; vínculos institucionais; vínculos à turmas e subturmas; restrições de ordem financeira ou disciplinar; necessidades educativas especiais e histórico de alterações e de documentos enviados. Em casos excepcionais, podem haver a necessidade de usar os dados cadastrais completos. De um professor, um administrador pode consultar: nome, e-mail, telefone, CPF, dados bancários, histórico de lecionamento na instituição e de afastamentos, produção acadêmica completa dentro e fora da instituição e as turmas e subturmas às quais aquele professor está vinculado. Além disso, ele deve permitir que o professor consulte de um aluno: nome, e-mail, telefone, CPF, histórico escolar, vínculos institucionais, vínculos à turmas e subturmas e restrições de ordem financeira ou disciplinar. |
| **REQ03** | Administrador | Atualizar Matrícula | O sistema deve permitir que o administrador edite a situação acadêmica de uma matrícula, a turma e subturmas às quais o aluno está registrado, restrições de ordem financeira ou disciplinar e, em casos excepcionais, corrigir o histórico do aluno, retificar seus dados cadastrais e necessidades educativas especiais, atualizar vínculos institucionais e o período letivo. |
| **REQ04** | Administrador | Desligar/Trancar Matrícula | O sistema deve permitir que o administrador tranque ou desligue uma matrícula de um aluno ou professor. |
| **REQ05** | Administrador | Cadastrar Disciplina | O sistema deve permitir que o administrador cadastre uma disciplina com um código, nome, base curricular e carga horária. |
| **REQ06** | Administrador, Professor e Aluno | Consultar Disciplina | O sistema deve permitir que o administrador, aluno ou professor, dado um código ou nome, possa consultar uma dada disciplina, vendo sua base curricular e carga horária. |
| **REQ07** | Administrador | Atualizar Disciplina | O sistema deve permitir que o administrador possa editar o nome, base curricular e carga horária de uma disciplina, dado um código. |
| **REQ08** | Administrador | Arquivar Disciplina | O sistema deve permitir que o administrador possa arquivar uma determinada disciplina, dado um código. |
| **REQ09** | Administrador | Cadastrar Turma/Subturma | O sistema deve permitir que o administrador, dada uma disciplina, possa criar uma turma ou subturma com um código único e vincular a elas, um nome, professor, o conjunto de alunos, o horário e a sala de aula. |
| **REQ10** | Administrador, Professor e Aluno | Consultar Turma/Subturma | O sistema deve permitir que o administrador, dado um código, possa consultar o nome, professor, o conjunto de alunos, horário e a sala de aula. Além disso, ele deve permitir que alunos e professores possam ver as turmas e subturmas as quais foram vinculados. |
| **REQ11** | Administrador | Atualizar Turma/Subturma | O sistema deve permitir que o administrador, dado um código, possa atualizar o nome, professor, o conjunto de alunos, o horário e a sala de aula de uma turma ou subturma. |
| **REQ12** | Administrador | Arquivar Turma/Subturma | O sistema deve permitir que o administrador, dado um código, possa arquivar uma turma e subturma. |
| **REQ13** | Professor | Adicionar Material Didático | O sistema deve permitir que o professor, dada uma turma ou subturma a qual esteja vinculado, seja capaz de adicionar qualquer tipo de material didático que lhe seja permitido. |
| **REQ14** | Administrador, Professor, Aluno | Consultar Material Didático | O sistema deve permitir que o professor ou aluno, dada uma turma ou subturma a qual esteja vinculado, seja capaz de consultar qualquer material didático associado àquela turma ou subturma. Em casos excepcionais, pode ser necessário que um administrador precise consultar o material didático de uma turma ou subturma e, por causa disso, o sistema também deve permitir que ele realize essa operação. |
| **REQ15** | Administrador, Professor | Atualizar Material Didático | O sistema deve permitir que o professor, dada uma turma ou subturma a qual esteja vinculado, seja capaz de atualizar qualquer material didático associado àquela turma ou subturma. Em casos excepcionais, pode ser necessário que um administrador precise atualizar o material didático de uma turma ou subturma e, por causa disso, o sistema também deve permitir que ele realize essa operação. |
| **REQ16** | Administrador, Professor | Excluir Material Didático | O sistema deve permitir que o professor, dada uma turma ou subturma a qual esteja vinculado, seja capaz de excluir qualquer material didático associado àquela turma ou subturma. Em casos excepcionais, pode ser necessário que um administrador preciso excluir o material didático de uma turma ou subturma e, por causa disso, o sistema também deve permitir que ele realize essa operação. |
| **REQ17** | Professor | Adicionar Avaliação | O sistema deve permitir que um professor, dada uma turma ou subturma a qual esteja vinculado, seja capaz de adicionar uma avaliação, seja ela apenas para lançamento de notas no boletim ou como uma atividade dentro da plataforma. Os seus campos devem conter: código, nome, descrição, valor total e tipo de atividade – envio de texto, envio de arquivo ou questionário. No caso de ser um questionário, deverão ser formuladas questões e um tempo limite deve ser informado. |
| **REQ18** | Administrador, Professor, Aluno | Consultar Avaliação | O sistema deve permitir que um professor ou aluno, dada uma turma ou subturma a qual estejam vinculados, sejam capazes de consultar qualquer avaliação. O professor deve ter acesso aos campos da atividade e, caso seja um questionário, à cada questão presente nele. Adicionalmente, ele deve ser capaz de ver a nota de cada aluno associado àquela dada avaliação. O aluno deve ser capaz de acessar cada campo associado à atividade e, caso seja um questionário, o aluno deve ter acesso a cada questão assim que tomar a decisão de iniciar o questionário. Além disso, o sistema deve permitir que, em casos excepcionais, o administrador consulte as avaliações da mesma forma que o professor. |
| **REQ19** | Administrador, Professor | Atualizar Avaliação | O sistema deve permitir que um professor, dada uma turma ou subturma a qual esteja vinculado, seja capaz de atualizar uma avaliação, alterando os campos da atividade e, caso seja um questionário, alterando também cada questão associada a ele. |
| **REQ20** | Administrador, Professor | Arquivar Avaliação | O sistema deve permitir que um professor, dada uma turma ou subturma a qual esteja vinculado, seja capaz de arquivar uma dada avaliação, removendo-a do boletim. Em casos excepcionais, o sistema deve permitir que o administrador execute essa ação. |
| **REQ21** | Administrador | Criar Lista de Presença | O sistema deve permitir que um administrador, dada uma turma ou subturma, crie uma lista de presença associada a um dia. |
| **REQ22** | Administrador, Professor, Aluno | Consultar Lista de Presença | O sistema deve permitir que um professor, dada uma turma ou subturma a qual esteja vinculado, possa consultar a lista de chamada referente a um dia ou a presença de um aluno, dado um nome ou matrícula. O sistema também deverá permitir que um aluno possa consultar sua presença referente a um determinado dia. Em casos excepcionais, o administrador pode precisar de consultar a lista de presença de um determinado aluno, dado um nome ou matrícula. |
| **REQ23** | Administrador, Professor | Atualizar Lista de Presença | O sistema deve permitir que um professor, dada uma turma ou subturma a qual esteja vinculado, atualize a lista de presença referente a um dia. Em casos excepcionais, o sistema deverá permitir que o administrador também atualize a lista de presença. |
| **REQ24** | Administrador | Arquivar Lista de Presença | O sistema deve permitir que um administrador, dada uma turma ou subturma, arquive a lista de presença referente a um dia. |
| **REQ25** | Administrador, Bibliotecário | Cadastrar Livros | O sistema deve permitir que um administrador, dado um livro, possa cadastrar ele no acervo, informando ISBN, título, autor, ano, edição, a biblioteca onde está disponível, status e código de identificação dentro do acervo da biblioteca. |
| **REQ26** | Administrador, Professor, Aluno, Bibliotecário, Convidado | Consultar Livro | O sistema deve permitir que qualquer administrador, professor, aluno ou convidado, informando ISBN, título, autor, ano, edição, biblioteca ou código de identificação, possa consultar sobre um determinado livro. |
| **REQ27** | Administrador, Bibliotecário | Atualizar Livro | O sistema deve permitir que um administrador possa editar os campos essenciais de um livro e alterar seu status, quando necessário. |
| **REQ28** | Administrador, Bibliotecário | Arquivar Livro | O sistema deve permitir que um administrador possa arquivar um determinado livro quando ele não estiver mais disponível no acervo. |
| **REQ29** | Administrador, Bibliotecário | Criar Empréstimo de Livro | O sistema deve permitir que um administrador, dado um livro, possa criar um empréstimo referente a ele, cadastrando o nome da pessoa, a matrícula, a data e a duração. |
| **REQ30** | Administrador, Professor, Aluno, Convidado, Bibliotecário | Consultar Empréstimo de Livro | O sistema deve permitir que um administrador, dado um livro, possa consultar todos os empréstimos referentes a ele. O professor, aluno e convidado podem usar dessa ação para checar o status e duração de um empréstimo atual, de forma a prever uma possível liberação de um livro. |
| **REQ31** | Administrador, Professor, Aluno, Bibliotecário | Atualizar Empréstimo de Livro | O sistema deve permitir que um administrador, sob pedido de quem realizou o empréstimo, possa atualizar um empréstimo referente a um livro, realizando uma renovação. Além disso, ele deve permitir que administradores, alunos e professores possam fazer a renovação de forma autônoma. |
| **REQ32** | Administrador, Bibliotecário | Arquivar Empréstimo de Livro | O sistema deve permitir que um administrador, sob pedido de quem realizou o empréstimo, possa arquivar um empréstimo referente a um livro, realizando uma devolução. |
| **REQ33** | Administrador, Professor, Aluno, Bibliotecário | Criar Reserva de Livro | O sistema deve permitir que um administrador, professor, aluno ou convidado faça o pedido de reserva de algum livro, o mantendo numa fila até ele estar disponível para que ele venha realizar um empréstimo. |
| **REQ34** | Administrador, Professor, Aluno, Convidado, Bibliotecário | Consultar Fila de Reserva de Livro | O sistema deve permitir que um administrador possa consultar todas reservas referentes a um determinado livro. O professor, aluno e convidado podem usar dessa ação para checar a fila de reservas, de forma a prever uma possível liberação de um livro. |
| **REQ35** | Administrador, Bibliotecário | Atualizar Fila de Reserva de Livro | O sistema deve permitir que um administrador, em casos excepcionais, possa atualizar a fila de reservas de um determinado livro, trocando a ordem dela. |
| **REQ36** | Administrador, Professor, Aluno, Bibliotecário | Arquivar/Deletar Reserva de Livro | O sistema deve permitir que um administrador possa arquivar uma reserva de livro, caso a pessoa que pediu a reserva não venha a atender no prazo limite ou caso um empréstimo tenha sido realizado por essa pessoa. Além disso, o sistema deve permitir que reservas ainda não atendidas ou efetuadas sejam deletadas da fila. |
| **REQ37** | Administrador, Professor | Cadastrar Bolsa | O sistema deve permitir que o administrador possa cadastrar programas de bolsa. Além disso, ele deve permitir que o professor possa cadastrar suas bolsas de pesquisa e extensão. |
| **REQ38** | Administrador, Professor, Aluno | Consultar Bolsa | O sistema deve permitir que administradores, professores e alunos possam consultar bolsas de auxílio, de pesquisa e extensão que estiverem disponíveis. |
| **REQ39** | Aluno | Pedir Associação à Bolsa | O sistema deve permitir que alunos possam pedir para se associar à determinada bolsa, enviando todos os campos necessários. |
| **REQ40** | Administrador, Professor | Aceitar Associação à Bolsa | O sistema deve permitir que administradores e professores possam aprovar associações a bolsas pelas quais eles sejam responsáveis. |
| **REQ41** | Administrador, Professor | Atualizar Bolsa | O sistema deve permitir que administradores e professores possam atualizar as bolsas pelas quais eles sejam responsáveis. |
| **REQ42** | Administrador, Professor | Arquivar Bolsa | O sistema deve permitir que administradores e professores possam arquivar as bolsas pelas quais eles sejam responsáveis. |
| **REQ43** | Administrador, Professor | Cadastrar Documento | O sistema deve permitir que administradores e professores possam cadastrar documentos a serem assinados por um remetente. Serão informados, assunto, descrição, o arquivo com o documento a ser assinado e o remetente como campos essenciais. |
| **REQ44** | Administrador, Professor, Aluno | Consultar Documento | O sistema deve permitir que administradores e professores possam consultar os documentos que enviaram para assinar, podendo checar os seus campos essenciais e status – se está assinado, não assinado, atrasado. |
| **REQ45** | Administrador, Professor, Aluno | Assinar Documento | O sistema deve permitir que administradores, professores e alunos que sejam remetentes para a assinatura de determinado documento, venham a assiná-lo. |
| **REQ46** | Administrador, Professor | Atualizar Documento | O sistema deve permitir que administradores, professores e alunos que tenham cadastrado um documento, possam editar seus campos essenciais. |
| **REQ47** | Administrador | Arquivar Documento | O sistema deve permitir que administradores possam arquivar documentos enviados para assinatura. |
| **REQ48** | Professor | Cadastrar Notícia | O sistema deve permitir que o professor, dada uma turma ou subturma a qual esteja vinculado, seja capaz de cadastrar uma notícia contendo: código (primary key), descrição, docente associado e data de concepção. |
| **REQ49** | Administrador, Professor, Aluno | Consultar Notícia | O sistema deve permitir que o administrador, professor ou aluno, dada uma turma ou subturma a qual esteja vinculado, seja capaz de consultar as notícias associadas àquela turma ou subturma, visualizando código, descrição, docente associado e data de concepção. |
| **REQ50** | Professor | Atualizar Notícia | O sistema deve permitir que o professor, dada uma turma ou subturma a qual esteja vinculado, seja capaz de atualizar as notícias por ele cadastradas, modificando a descrição e data de concepção. |
| **REQ51** | Professor | Arquivar Notícia | O sistema deve permitir que o professor, dada uma turma ou subturma a qual esteja vinculado, seja capaz de arquivar notícias por ele cadastradas. |
| **REQ52** | Professor | Cadastrar Cronograma | O sistema deve permitir que o professor, dada uma turma ou subturma a qual esteja vinculado, seja capaz de cadastrar um cronograma contendo registros com: código (primary key), título, descrição e data para cada evento/atividade. |
| **REQ53** | Administrador, Professor, Aluno | Consultar Cronograma | O sistema deve permitir que o administrador, professor ou aluno, dada uma turma ou subturma a qual esteja vinculado, seja capaz de consultar o cronograma associado àquela turma ou subturma, visualizando todos os registros com seus códigos, títulos, descrições e datas. |
| **REQ54** | Professor | Atualizar Cronograma | O sistema deve permitir que o professor, dada uma turma ou subturma a qual esteja vinculado, seja capaz de atualizar o cronograma, modificando, adicionando ou removendo registros de eventos/atividades. |
| **REQ55** | Professor | Arquivar Cronograma | O sistema deve permitir que o professor, dada uma turma ou subturma a qual esteja vinculado, seja capaz de arquivar o cronograma da turma ou subturma. |

## Regras de Negócio

| Id    | Nome | Descrição |
|-------|------|-----------|
| **RN01** | Cadastro Restrito | Apenas administradores podem cadastrar professores, alunos e outros administradores |
| **RN02** | Consulta Limitada de Alunos por Professores | Professores só podem consultar dados limitados dos alunos (nome, e-mail, telefone, CPF, histórico escolar, vínculos e restrições) |
| **RN03** | Acesso Completo Excepcional | Administradores podem acessar dados completos dos alunos apenas em casos excepcionais |
| **RN04** | Correção Excepcional de Histórico | A correção do histórico do aluno só pode ser feita pelo administrador em casos excepcionais |
| **RN05** | Trancamento/Desligamento por Administradores | Apenas administradores podem trancar ou desligar matrículas |
| **RN06** | Código Único de Disciplina | Cada disciplina deve possuir um código único identificador |
| **RN07** | Código Único de Turma/Subturma | Turmas e subturmas devem ter código único vinculado a uma disciplina |
| **RN08** | Visualização por Vínculo | Alunos e professores só podem visualizar as turmas às quais estão vinculados |
| **RN09** | Arquivamento por Administradores | Apenas administradores podem arquivar disciplinas, turmas e subturmas |
| **RN10** | Gestão de Materiais por Vínculo | Professores só podem gerenciar materiais didáticos das turmas às quais estão vinculados |
| **RN11** | Acesso Excepcional a Materiais | Administradores só podem acessar/gerenciar materiais didáticos em casos excepcionais |
| **RN12** | Configuração de Questionários | Avaliações do tipo questionário devem ter tempo limite definido e questões formuladas |
| **RN13** | Acesso a Questões | Alunos só podem visualizar as questões do questionário após iniciá-lo |
| **RN14** | Visualização de Notas Restrita | Apenas professores podem visualizar as notas dos alunos nas avaliações. Os alunos só teram acesso as notas quando estas forem postas no boletim |
| **RN15** | Arquivamento de Avaliações | Apenas professores podem arquivar avaliações (administradores apenas em casos excepcionais) |
| **RN16** | Criação de Listas de Chamada| Listas de chamada são criadas pelos administradores e vinculadas a datas específicas |
| **RN17** | Atualização de Presença por Professor | Professores podem atualizar listas de presença apenas das suas turmas |
| **RN18** | Consulta Individual de Frequência | Alunos só podem consultar sua própria frequência |
| **RN19** | Arquivamento de Listas de Chamada | Apenas administradores podem arquivar listas de chamada |
| **RN20** | Acesso de Convidados à Biblioteca | Convidados podem consultar livros e filas de reserva, mas não podem realizar empréstimos |
| **RN21** | Gestão de Livros por Administradores | Apenas administradores podem cadastrar, atualizar e arquivar livros |
| **RN22** | Gestão de Empréstimos | Apenas administradores podem arquivar empréstimos de livros |
| **RN23** | Renovação Autônoma | Renovações de empréstimos podem ser feitas de forma autônoma por administradores, professores e alunos |
| **RN24** | Sistema de Fila de Reservas | Reservas de livros mantêm os usuários em fila até a disponibilidade do livro |
| **RN25** | Modificação de Fila | Apenas administradores podem modificar a ordem da fila de reservas (casos excepcionais) |
| **RN26** | Arquivamento de Reservas | Reservas podem ser arquivadas se o usuário não atender no prazo ou após empréstimo realizado |
| **RN27** | Cadastro de Bolsas por Perfil | Administradores cadastram programas de bolsa; professores cadastram bolsas de pesquisa/extensão |
| **RN28** | Solicitação de Bolsas por Alunos | Apenas alunos podem solicitar associação a bolsas |
| **RN29** | Aprovação de Associações | Aprovação de associações a bolsas é feita pelos responsáveis (administradores ou professores) |
| **RN30** | Gestão de Bolsas por Responsáveis | Apenas os responsáveis pela bolsa podem atualizá-la ou arquivá-la |
| **RN31** | Cadastro de Documentos | Apenas administradores e professores podem cadastrar documentos para assinatura |
| **RN32** | Campos Obrigatórios de Documentos | Documentos devem conter: assunto, descrição, arquivo e remetente como campos obrigatórios |
| **RN33** | Assinatura por Remetente Designado | Apenas o remetente designado pode assinar um documento |
| **RN34** | Edição por Criador do Documento | Apenas quem cadastrou o documento pode editá-lo |
| **RN35** | Arquivamento de Documentos | Apenas administradores podem arquivar documentos |
| **RN36** | Registro de Operações Excepcionais | Operações realizadas por administradores em casos excepcionais devem ser registradas em log |
| **RN37** | Identificação Única de Entidades | Todas as entidades (matrículas, disciplinas, turmas, etc.) devem ter códigos únicos identificadores |
| **RN38** | Controle de Acesso por Perfil | Usuários só podem executar operações de acordo com seu perfil e vínculos institucionais |
| **RN39** | Política de Arquivamento | Operações de arquivamento não excluem dados, apenas os tornam inativos no sistema |
| **RN40** | Acesso Público a Consultas | Consultas públicas (livros, bolsas) podem ser acessadas por convidados sem autenticação |


## Casos de Uso

| Id    | Nome                         | Requisitos                                                                 | Regra de Negócio |
|-------|------------------------------|----------------------------------------------------------------------------|------------------|
| **CSU01** | Cadastro                     | REQ01, REQ05, REQ09, REQ13, REQ17, REQ21, REQ25, REQ29, REQ33, REQ37, REQ43 | RN01, RN37      |
| **CSU02** | Autenticação (ou login)      | REQ02                                                                      | RN37             |
| **CSU03** | Gestão de curso              | REQ05, REQ06, REQ07, REQ08                                                 | RN02, RN06, RN09, RN37, RN38, RN39 |
| **CSU04** | Gestão de matrícula          | REQ01, REQ02, REQ03, REQ04                                                 | RN05, RN37       |
| **CSU05** | Gestão de turma e subturma   | REQ09, REQ10, REQ11, REQ12                                                 | RN02, RN03, RN07, RN09, RN36, RN37, RN39 |
| **CSU06** | Gestão de material didático  | REQ13, REQ14, REQ15, REQ16                                                 | RN10, RN11, RN36, RN37 |
| **CSU07** | Gestão de avaliações         | REQ17, REQ18, REQ19, REQ20                                                 | RN12, RN14, RN15, RN36, RN37, RN39 |
| **CSU08** | Gestão de lista de chamada   | REQ21, REQ22, REQ23, REQ24                                                 | RN16, RN17, RN18, RN19, RN37, RN39 |
| **CSU09** | Gestão de livros             | REQ25, REQ26, REQ27, REQ28, REQ29, REQ30, REQ31, REQ32, REQ33, REQ34, REQ35, REQ36 | RN21, RN22, RN23, RN25, RN26, RN36, RN37, RN39 |
| **CSU10** | Gestão de bolsas             | REQ37, REQ38, REQ39, REQ40, REQ41, REQ42                                   | RN27, RN28, RN29, RN30, RN37, RN38 |
| **CSU11** | Gestão de documentos         | REQ43, REQ44, REQ45, REQ46, REQ47                                          | RN04, RN31, RN32, RN33, RN34, RN35, RN36, RN38, RN39 |
| **CSU12** | Acessar boletim              | REQ18                                                                      | RN14, RN38       |
| **CSU13** | Acesso às atividades e a seu estado            | REQ17, REQ18                                                               | RN12, RN13, RN38 |
| **CSU14** | Acessar turmas e material associado | REQ10, REQ14, REQ18                                                 | RN02, RN03, RN08, RN36, RN38 |
| **CSU15** | Acessar e usar a biblioteca  | REQ26, REQ29, REQ30, REQ31, REQ33, REQ34, REQ35                            | RN20, RN23, RN24, RN38, RN40 |

## Atualizações

### 20/10/25

No dia 20/10/25, foram feitas algumas mudanças quanto aos casos de uso. Percebemos que alguns estavam complexos de mais e outros poderiam ser inclusos em CSUs que eram praticamente a mesma coisa ou tinham uma abordagem muito semelhante. Então optamos por dividir alguns em CSUs menores e agrupar outros.

O resultado ficou da seguinte forma:

## Casos de Uso

| Id        | Nome                                       | Requisitos                                                                         | Regra de Negócio |
|-----------|--------------------------------------------|------------------------------------------------------------------------------------|------------------|
| **CSU01** | Login e cadastro Auxiliar                  | REQ01, REQ02, REQ05, REQ09, REQ13, REQ17, REQ21, REQ25, REQ29, REQ33, REQ37, REQ43 | RN01, RN37      |
| **CSU02** | Gestão de matrícula                        | REQ01, REQ02, REQ03, REQ04                                                         | RN05, RN37       |                                                                     |              |
| **CSU03** | Gestão disciplinas                         | REQ05, REQ06, REQ07, REQ08                                                         | RN02, RN06, RN09, RN37, RN38, RN39 |
| **CSU04** | Gestão de turma e subturma                 | REQ09, REQ10, REQ11, REQ12                                                         | RN02, RN03, RN07, RN09, RN36, RN37, RN39 |
| **CSU05** | Gestão de material didático                | REQ13, REQ14, REQ15, REQ16                                                         | RN10, RN11, RN36, RN37 |
| **CSU06** | Gestão de avaliações                       | REQ17, REQ18, REQ19, REQ20                                                         | RN12, RN14, RN15, RN36, RN37, RN39 |
| **CSU07** | Gestão de lista de chamada                 | REQ21, REQ22, REQ23, REQ24                                                         | RN16, RN17, RN18, RN19, RN37, RN39 |
| **CSU08** | Gestão de livros                           | REQ25, REQ26, REQ27, REQ28                                                         | RN21, RN37, RN39 |
| **CSU09** | Gestão de emprestimos e reservas de livros | REQ29, REQ30, REQ31, REQ32, REQ33, REQ34, REQ35, REQ36                             | RN22, RN23, RN25, RN26, RN36, RN37, RN39 |
| **CSU10** | Gestão de bolsas                           | REQ37, REQ38, REQ39, REQ40, REQ41, REQ42                                           | RN27, RN28, RN29, RN30, RN37, RN38 |
| **CSU11** | Gestão de documentos                       | REQ43, REQ44, REQ45, REQ46, REQ47                                                  | RN04, RN31, RN32, RN33, RN34, RN35, RN36, RN38, RN39 |
| **CSU12** | Acessar boletim                            | REQ18                                                                              | RN14, RN38       |
| **CSU13** | Fazer atividades/questionários             | REQ17, REQ18                                                                       | RN12, RN13, RN38 |
| **CSU14** | Acessar turmas e material associado        | REQ10, REQ14, REQ18                                                                | RN02, RN03, RN08, RN36, RN38 |
| **CSU15** | Acessar e usar a biblioteca                | REQ26, REQ29, REQ30, REQ31, REQ32, REQ33, REQ34, REQ35, REQ36                      | RN20, RN23, RN24, RN38, RN40 |
| **CSU16** | Gestão de notícias                         | REQ48, REQ49, REQ50, REQ51                                                         | RN20, RN23, RN24, RN38, RN40 |
| **CSU17** | Gestão de cronogramas                      | REQ52, REQ53, REQ54, REQ55                                                         | RN20, RN23, RN24, RN38, RN40 |
| **CSU18** | Atribuição de notas                        | REQ26, REQ29, REQ30, REQ31, REQ32, REQ33, REQ34, REQ35, REQ36                      | RN14, RN20, RN23, RN24, RN38, RN40 |

### 16/12/25

No dia 16/12/25, foi modificado o CSU13, pois ele englobava necessidas que já haviam sido supridas em um CSU anterior (CSU06). Então, para ele foi destinado uma função crucial que ainda não havia sido posta ao sistema: uma tela exclusiva para o acesso de atividades e uma sistema de estado para as atividades.

O resultado ficou da seguinte forma:

## Casos de Uso

| Id        | Nome                                       | Requisitos                                                                         | Regra de Negócio |
|-----------|--------------------------------------------|------------------------------------------------------------------------------------|------------------|
| **CSU01** | Login e cadastro Auxiliar                  | REQ01, REQ02, REQ05, REQ09, REQ13, REQ17, REQ21, REQ25, REQ29, REQ33, REQ37, REQ43 | RN01, RN37      |
| **CSU02** | Gestão de matrícula                        | REQ01, REQ02, REQ03, REQ04                                                         | RN05, RN37       |                                                                     |              |
| **CSU03** | Gestão disciplinas                         | REQ05, REQ06, REQ07, REQ08                                                         | RN02, RN06, RN09, RN37, RN38, RN39 |
| **CSU04** | Gestão de turma e subturma                 | REQ09, REQ10, REQ11, REQ12                                                         | RN02, RN03, RN07, RN09, RN36, RN37, RN39 |
| **CSU05** | Gestão de material didático                | REQ13, REQ14, REQ15, REQ16                                                         | RN10, RN11, RN36, RN37 |
| **CSU06** | Gestão de avaliações                       | REQ17, REQ18, REQ19, REQ20                                                         | RN12, RN14, RN15, RN36, RN37, RN39 |
| **CSU07** | Gestão de lista de chamada                 | REQ21, REQ22, REQ23, REQ24                                                         | RN16, RN17, RN18, RN19, RN37, RN39 |
| **CSU08** | Gestão de livros                           | REQ25, REQ26, REQ27, REQ28                                                         | RN21, RN37, RN39 |
| **CSU09** | Gestão de emprestimos e reservas de livros | REQ29, REQ30, REQ31, REQ32, REQ33, REQ34, REQ35, REQ36                             | RN22, RN23, RN25, RN26, RN36, RN37, RN39 |
| **CSU10** | Gestão de bolsas                           | REQ37, REQ38, REQ39, REQ40, REQ41, REQ42                                           | RN27, RN28, RN29, RN30, RN37, RN38 |
| **CSU11** | Gestão de documentos                       | REQ43, REQ44, REQ45, REQ46, REQ47                                                  | RN04, RN31, RN32, RN33, RN34, RN35, RN36, RN38, RN39 |
| **CSU12** | Acessar boletim                            | REQ18                                                                              | RN14, RN38       |
| **CSU13** | Fazer atividades/questionários             | REQ17, REQ18                                                                       | RN12, RN13, RN38 |
| **CSU14** | Acessar turmas e material associado        | REQ10, REQ14, REQ18                                                                | RN02, RN03, RN08, RN36, RN38 |
| **CSU15** | Acessar e usar a biblioteca                | REQ26, REQ29, REQ30, REQ31, REQ32, REQ33, REQ34, REQ35, REQ36                      | RN20, RN23, RN24, RN38, RN40 |
| **CSU16** | Gestão de notícias                         | REQ48, REQ49, REQ50, REQ51                                                         | RN20, RN23, RN24, RN38, RN40 |
| **CSU17** | Gestão de cronogramas                      | REQ52, REQ53, REQ54, REQ55                                                         | RN20, RN23, RN24, RN38, RN40 |
| **CSU18** | Atribuição de notas                        | REQ26, REQ29, REQ30, REQ31, REQ32, REQ33, REQ34, REQ35, REQ36                      | RN14, RN20, RN23, RN24, RN38, RN40 |

## Planejamento

| **Sprint**   | **Duração** | **CSUs (Casos de Uso)**                                                                                                                                                                                                                                                                         | **Desenvolvedores Responsáveis**                                                                                                                                                                                                      |
| ------------ | ----------- | ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| **Sprint 1** | 3 semanas   | CSU01 – Login e Cadastro Auxiliar<br>CSU02 – Gestão de Matrícula<br>CSU04 – Gerenciar turmas/subturmas <br>CSU06 – Gestão de Atividades<br>CSU07 – Gerenciar listas de presença                                                                                                                                                       | **César:** CSU06<br>**Davi:** CSU04<br>**Jean:** CSU02<br>**Luiza:** CSU01<br>**Wesley:** CSU07              |
| **Sprint 2** | 2 semanas   | CSU03 – Gestão de Disciplinas<br>CSU05 – Gerenciar Material Didático<br>CSU10 – Gestão de Bolsas<br>CSU11 – Gestão de Documentos<br>CSU13 – Fazer atividades e questionários<br>CSU17 – Gestão de Cronogramas                                                                                                                   | **César:** CSU13<br>**Davi:** CSU05 e CSU17<br>**Jean:** CSU11<br>**Luiza:** CSU03<br>**Wesley:** CSU10          |
| **Sprint 3** | 2 semanas   | CSU08 – Gestão de Livros<br>CSU09 – Gestão de Empréstimos e Reservas de Livros<br>CSU12 – Acessar Boletim<br>CSU14 – Acessar Turmas e Materiais Associados<br>CSU15 – Acessar e Usar a Biblioteca<br>CSU16 – Gestão de Notícias<br>CSU18 – Atribuição de Notas | **César:** CSU12 e CSU18<br>**Davi:** CSU14<br>**Jean:** CSU08 e CSU15<br>**Luiza:** CSU09<br>**Wesley:** CSU16 |

