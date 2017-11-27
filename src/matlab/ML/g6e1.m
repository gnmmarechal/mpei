Membros  = {"Espanha", "Portugal", "Itália", "EUA", "Alemanha"};
Teste = {"Itália", "Polónia", "França", "Espanha", "EUA", "Alemanha"};

m = 100;
k = 3;

B = Inicializar(m);

for i = 1:length(Membros)
  B = IncluirMembroCount(B, k, Membros{i});
end

% Teste do Count Filter, remover um país
B = RemoverMembro(B, k, "EUA");

for i = 1:length(Teste)
  ok = PertenceMembro(B, k, Teste{i});
  val = " NAO";
  if ok
    val = " SIM";
  end
  printf("%s %s\n", Teste{i}, val);
end