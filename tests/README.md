# Testes de persistência

Com JDK 25:

```sh
javac -encoding UTF-8 -d target/test-classes *.java tests/PersistenceSecurityTest.java
java -cp target/test-classes br.gerenciamento.petshop.PersistenceSecurityTest
```

Os testes usam somente uma pasta temporária. Verificam delimitadores, quebras de linha, nomes com acentos, arquivos inválidos, IDs duplicados e preservação do estado após falha.

Dados reais permanecem em texto local e devem ficar em uma pasta de acesso restrito do usuário. Não publique esse arquivo: ele está excluído pelo .gitignore. A gravação usa substituição atômica; se o sistema de arquivos não suportar essa operação, o salvamento falha preservando o arquivo anterior.
