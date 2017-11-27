function cena = Coisa(inteiro)
  while inteiro>0
    cena = [cena num2str(inteiro)];
    inteiro -= 1;
  end
end