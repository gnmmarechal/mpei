function cena = Coisa(inteiro) % Serve para manter o comportamento semelhante ao dos programas anteriores.
  cenas=""
  cena=""
  while inteiro>0
    cenas = [cenas num2str(inteiro)];
    inteiro -= 1;
  end
  cena = flip(cenas);
end