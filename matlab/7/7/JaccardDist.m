function J = JaccardDist(Nu,Set)
  J = zeros(Nu); 
  h = waitbar(0, "Calculating Jaccard Distance...");
  for n1 = 1:Nu,
    waitbar(n1/Nu, h);
    for n2= n1+1:Nu,
      a = length(intersect(Set{n1}, Set{n2}));
      J(n1,n2) = 1 - a/(length(Set{n1}) + length(Set{n2} )- a);
    end
  end
  delete(h)
end