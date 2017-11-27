Function Set = ListFilm(Nu,users)

  Set = cell(Nu, 1);

  for n = 1:Nu,
    ind = find(u(:,1) == users(n));   
    Set{n} = [Set{n} u(ind,2)];
  end
  
end