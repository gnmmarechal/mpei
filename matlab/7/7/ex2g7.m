tic
udata = load("u2.data"); % usar u2.data para menos dados

u = udata(1:end, 1:2);
clear udata;

users = unique(u(:,1));
Nu = length(users);

Set = cell(Nu, 1);

for n = 1:Nu,
  ind = find(u(:,1) == users(n));
  Set{n} = [Set{n} u(ind,2)];
end

% Datasample não existe em Octave
x = 5
Set = datasample(Set, x);
% Jaccard

printf("Jaccard Distance\n");
J = JaccardDist(Nu, Set);

toc
threshold = input("Limiar: ")
tic
[SimilarUsers,k] = GetSimilarUsers(Nu, users, J, threshold);

printf("Pares de utilizadores com distância inferior a %f: %d\n", threshold, k-1)
toc

SimilarUsers
save("Matriz.txt", "J");