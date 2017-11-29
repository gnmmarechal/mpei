udata = load("u.data");
% Fica apenas com as duas primeiras colunas
u = udata(1:end, 1:2);
clear udata;

% Lista de utilizadores
users = unique(u(:,1));
Nu = length(users);

% Constroi a lista de filmes para cada utilizador
Set = cell(Nu, 1);

for n = 1:Nu,
  %Obter filmes de cada um
  ind = find(u(:,1) == users(n));
  % E guarda num array. Usa celulas porque utilizador tem um numero
  % diferente de filmes. Se fossem iguais podia ser um array
  Set{n} = [Set{n} u(ind,2)];
end


%% Calcula a distancia de Jaccard entre todos os pares pela definicao.
tic
J = zeros(Nu); 
h = waitbar(0, "Calculating");
for n1 = 1:Nu,
  waitbar(n1/Nu, h);
  for n2= n1+1:Nu,
    a = length(intersect(Set{n1}, Set{n2}));
    J(n1,n2) = 1 - a/(length(Set{n1}) + length(Set{n2})- a);
  end
end
delete(h)
toc
%1)
prompt = "Limiar de distancia: ";
threshold = input(prompt)

% Array para guardar pares similares (user1, user2, distancia)
SimilarUsers= zeros(1,3);
k= 1;
for n1= 1:Nu,
  for n2= n1+1:Nu,
    if J(n1,n2)<threshold
       SimilarUsers(k,:)= [users(n1) users(n2) J(n1,n2)];
       k= k+1;
    end
  end
end
printf("Existem %d pares de utilizador em que a dist de Jaccard e menor que %f.\n",k-1,threshold)
%2)
SimilarUsers

save("Matriz DJac.txt","J")

