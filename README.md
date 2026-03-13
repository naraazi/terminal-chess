# Terminal Chess

Implementação de um jogo de xadrez para terminal em Java, com foco em modelagem orientada a objetos e regras clássicas da partida. O projeto executa localmente para dois jogadores no mesmo terminal e separa a aplicação em três camadas principais: interface de console, motor de xadrez e infraestrutura genérica de tabuleiro.

## Visão Geral

O jogo apresenta:

- tabuleiro 8x8 renderizado no terminal com destaque visual para movimentos possíveis;
- entrada por coordenadas no formato algébrico (`a1` a `h8`);
- controle de turnos entre brancas e pretas;
- validação de jogadas inválidas com mensagens de erro;
- captura de peças e exibição do material capturado;
- suporte a `check`, `checkmate`, `castling`, `en passant` e promoção de peão.

O ponto de entrada da aplicação é a classe [`Program.java`](/home/lorenzo/desenvolvimento/terminal-chess/src/application/Program.java), que conduz o loop principal da partida.

## Arquitetura

O código está organizado por responsabilidade:

- [`src/application`](/home/lorenzo/desenvolvimento/terminal-chess/src/application): camada de interface de console. Lê entradas, limpa a tela, imprime o tabuleiro e apresenta o estado da partida.
- [`src/chess`](/home/lorenzo/desenvolvimento/terminal-chess/src/chess): motor da partida de xadrez. Centraliza regras, turno, verificação de `check`/`checkmate`, promoção e lances especiais.
- [`src/chess/pieces`](/home/lorenzo/desenvolvimento/terminal-chess/src/chess/pieces): implementações concretas de movimento para cada tipo de peça.
- [`src/boardgame`](/home/lorenzo/desenvolvimento/terminal-chess/src/boardgame): camada genérica de tabuleiro, posições, peças abstratas e exceções reutilizáveis.

### Fluxo principal

1. A aplicação instancia uma partida em [`ChessMatch.java`](/home/lorenzo/desenvolvimento/terminal-chess/src/chess/ChessMatch.java).
2. O usuário informa a posição de origem.
3. O sistema calcula e destaca os movimentos possíveis da peça selecionada.
4. O usuário informa a posição de destino.
5. O motor valida a jogada, executa o movimento, processa capturas e avalia estados especiais da partida.
6. Em caso de promoção, a interface solicita a nova peça (`B`, `N`, `R`, `Q`).

## Regras e comportamentos implementados

- Movimentação individual de rei, dama, torre, bispo, cavalo e peão.
- Validação para impedir jogadas com peças do adversário.
- Bloqueio de jogadas que deixem o próprio rei em `check`.
- Detecção de `check` ao adversário após cada jogada.
- Detecção de `checkmate` por simulação das respostas possíveis.
- Roque pequeno e grande quando as condições da regra são atendidas.
- `En passant` com rastreamento temporário do peão vulnerável.
- Promoção automática para dama por padrão, com substituição opcional via entrada do jogador.

## Requisitos

- JDK 17 ou superior.
- Terminal com suporte razoável a ANSI para melhor experiência visual.

Observações:

- O README anterior mencionava Java 8, mas isso não corresponde ao código atual.
- Em alguns terminais de IDE, a limpeza de tela e as cores ANSI podem não funcionar perfeitamente.
- Em Windows, terminais como Git Bash, Windows Terminal ou similares tendem a oferecer melhor compatibilidade visual.

## Como executar

### Compilação manual

Na raiz do projeto:

```bash
mkdir -p out
javac -d out $(find src -name "*.java")
```

### Execução

```bash
java -cp out application.Program
```

## Como jogar

Durante a partida:

- informe a origem da jogada em `Source` usando coordenadas como `e2`;
- informe o destino em `Target` usando coordenadas como `e4`;
- casas destacadas em azul representam movimentos possíveis da peça selecionada;
- as peças capturadas são exibidas abaixo do tabuleiro;
- quando houver promoção, escolha entre `B`, `N`, `R` ou `Q`.

## Estrutura do projeto

```text
src/
  application/
    Program.java
    UI.java
  boardgame/
    Board.java
    BoardException.java
    Piece.java
    Position.java
  chess/
    ChessMatch.java
    ChessException.java
    ChessPiece.java
    ChessPosition.java
    Color.java
    pieces/
      Bishop.java
      King.java
      Knight.java
      Pawn.java
      Queen.java
      Rook.java
```

## Limitações atuais

- O projeto não possui suíte automatizada de testes.
- A partida é exclusivamente local, sem IA e sem multiplayer em rede.
- Não há sistema de persistência, histórico de jogadas ou exportação PGN/FEN.
- A interface é orientada a terminal e depende de suporte a ANSI para a melhor representação visual.

## Licença

Distribuído sob a licença MIT. Veja [`LICENSE`](/home/lorenzo/desenvolvimento/terminal-chess/LICENSE).
