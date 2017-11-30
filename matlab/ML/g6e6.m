% Ex. 6 Parte 2

filetext = fileread("oxfordbook.txt");

% Limpar o array
TF = (strcmp(filetext, "") == 1);
filetext(TF) = [];
p = [".","?","!",",",";",":", "_", "--"];
for i = 1:length(p)
  filetext = strrep(filetext, p(i), " ");
end
filetext = strsplit(filetext);


% Armazenar dados

m = 1000;
k = 4;

B = Inicializar(m);

for i = 1:length(filetext)
  B = IncluirMembroCount(B, k, filetext{i});
end
  