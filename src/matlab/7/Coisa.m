function cena = Coisa(inteiro)
  cenas=""
  cena=""
  while inteiro>0
    cenas = [cenas num2str(inteiro)];
    inteiro -= 1;
  end
  cena = flip(cenas);
end