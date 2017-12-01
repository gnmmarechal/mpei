function [SimilarUsers, k] = GetSimilarUsers(Nu, users, J, threshold)
  SimilarUsers = zeros(1, 3);
  k = 1;
  for n1= 1:Nu,
    for n2= n1+1:Nu,
      if J(n1,n2) < threshold
         SimilarUsers(k,:)= [users(n1) users(n2) J(n1,n2)];
         k += 1;
      end
    end
  end   
end