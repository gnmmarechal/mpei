function minsig = CalcDistMin(Nu,Set)
  tic
  h = waitbar(0, "Calculating");
  k = 10; %nr hashfunctions / tamanho da assinatura
  minsig = zeros(length(Set),k)
  for l=1:length(Set) 
    waitbar(l/Nu,h);
    for i=1:k 
      mini = 2^32-1;
      for ll=1:length(Set{l})
        n = [num2str(Set{l}(ll)) Coisa(i)];
        hash = string2hash(n,"djb2");
        if hash<mini
          mini = hash;
        end      
      end
      minsig(l,i)=mini;    
    end    
  end  
  delete(h)
  mini
  toc
end