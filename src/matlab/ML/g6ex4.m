tic
m = 8000;
nsM = 1000;
nsT = 10000;
n = 40; % String length
kmax = 15;
alpha = ['a':'z' 'A':'Z'];


Membros = {""};
Teste = {""};
ficheiro = {'pg21209.txt'};
pmfPT = pmfLetrasPT(ficheiro, alpha);
cpmfpt= cumsum(pmfPT);

for k=1:nsM
  Membros(k)=genchv(alpha, n, cpmfpt);
end

for k=1:nsT
  Teste(k)=genchv(alpha, n, cpmfpt); 
end  


nsuc = zeros(1, kmax);

for k=1:kmax
  
  B = Inicializar(m);
  
  for i = 1:length(Membros)
    B = IncluirMembro(B,k,Membros{i});
  end

  for i = 1:length(Teste)
    ok = PertenceMembro(B,k,Teste{i});
    if ok
      nsuc(k)+= 1;
    end
  end
end


ninter = length(intersect(Membros, Teste))

nsuc = nsuc - ninter;

pteo=zeros(1,kmax);% Probabilidades de falsos positivos as per
% https://en.wikipedia.org/wiki/Bloom_filter#Probability_of_false_positives

for k=1:kmax
  pteo(k) = (1-(1-1/m).^(k*nsM)).^k;
end

figure(1)
subplot(121)
plot(1:kmax, nsuc)

xlabel("Hash Functions")
ylabel("Number of F. P.")
subplot(122)
plot(1:kmax, pteo)
xlabel("Hash Functions")
ylabel("Probabilty of F. P.(Theoretical)")


toc