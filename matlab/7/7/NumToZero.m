function tempStr = NumToZero(inteiro) % Serve para manter o comportamento semelhante ao dos programas anteriores.
  tempStr = "";
  while inteiro > 0
    tempStr = [tempStr num2str(inteiro)];
    inteiro -= 1;
  end
  tempStr = flip(tempStr);
end