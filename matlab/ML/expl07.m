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

J = zeros(Nu);
h = waitbar(0, "Calculating");
for n1 = 1:Nu,
  waitbar(n1/Nu, h);
  for n2= n1+1:Nu,
    a = length(intersect(Set{n1}, Set{n2}));
    J(n1,n2) = 1 - a/(length(Set{n1}) + length(Set{n2} - a))
  end
end
delete(h)  