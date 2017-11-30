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

% Jaccard

printf("Jaccard Distance\n");
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
threshold = input("Limiar: ")

SimilarUsers = zeros(1, 3);
tic
k = 1;
for n1 = 1:Nu,
  for n2 = n1+1:Nu,
    if J(n1,n2) < threshold
      SimilarUsers(k, :) = [users(n1) users(n2) J(n1, n2)];
      k += 1;
    end
  end  
end 

printf("Pares de utilizadores com distância inferior a %f: %d\n", threshold, k-1)
toc

SimilarUsers
save("Matriz.txt", "J");