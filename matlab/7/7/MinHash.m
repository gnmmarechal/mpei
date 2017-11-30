function signature = MinHash(Nu,Set)
  tic
  h = waitbar(0, "MinHashing");
  k = 20; % Signature size
  signature = zeros(length(Set),k);
  for i=1:length(Set) 
    waitbar(i/Nu,h);
    hashVal = "";
   
    for j=1:k 
      minv = 2^32-1;
      hashVal = [hashVal NumToZero(j)];
      
      for m=1:length(Set{i})
        n = [num2str(Set{i}(m)) hashVal];
        hash = string2hash(n,"sdbm");
        if hash < minv
          minv = hash;
        end      
      end
      signature(i,j) = minv;    
    end    
  end  
  delete(h)
  toc
end