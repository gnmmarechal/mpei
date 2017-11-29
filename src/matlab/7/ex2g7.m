%Utiliza 2 ficheiros CalcDist.m e ProcDist.m
udata = load("u23.data");
  
u = udata(1:end, 1:2);
clear udata;

% Lista de users
users = unique(u(:,1));
Nu = length(users);

% Construir lista de filmes para cada user
Set = cell(Nu, 1);

for n = 1:Nu,
  %Obter filmes de cada um
  ind = find(u(:,1) == users(n));
  
  Set{n} = [Set{n} u(ind,2)];
end
  
%Calcular distancia de Jaccard
%Função devolve a matriz de Distâncias e o tempo que demorou a calcular

J = CalcDist(Nu,Set);

%Processar as distancias a um determinado Limiar de distancia
%Devolve Matriz com Pares de semelhança e nr de pares

threshold = 0.4;
SimilarUsers = ProcDist(Nu,users,J,threshold)
