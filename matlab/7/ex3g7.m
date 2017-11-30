%Utiliza string2hash.m  e CalcDistMin.m <- Minhashing
udata = load("u.data");

u = udata(1:end, 1:2);
clear udata;


users = unique(u(:,1));
Nu = length(users);

Set = cell(Nu, 1);

for n = 1:Nu,
  ind = find(u(:,1) == users(n));
  Set{n} = [Set{n} u(ind,2)];
end


%% Minhashing
J = zeros(Nu);
minsign = CalcDistMin(Nu,Set);
tic
h = waitbar(0, "Calculating");
for n1 = 1:Nu,
  waitbar(n1/Nu, h);
  for n2= n1+1:Nu,
    a = length(intersect(minsign(n1,:), minsign(n2,:)));
    J(n1,n2) = 1 - a/(length(minsign(n1,:)) + length(minsign(n2,:))-a);
  end
end
delete(h)
toc

threshold = 0.6;

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



