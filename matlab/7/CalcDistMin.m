function minsign = CalcDistMin(Nu,Set)
  tic
  h = waitbar(0, "Min-Hashing");
  k = 20; %nr hashfunctions / tamanho da assinatura
  minsign = zeros(length(Set),k);
  for l=1:length(Set) 
    waitbar(l/Nu,h);
    hashind = "";
    for i=1:k 
      mini = 2^32-1;
      hashind = [hashind num2str(k)];
      for ll=1:length(Set{l})
        n = [num2str(Set{l}(ll)) hashind];
        hash = string2hash(n,"sdbm");
        if hash<mini
          mini = hash;
        end      
      end
      minsign(l,i)=mini;    
    end    
  end  
  delete(h)
  mini
  toc
end