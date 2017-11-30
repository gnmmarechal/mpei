function ok = PertenceMembro(B, k, member)
  ok = true;
  for i = 1:k
    member = [member num2str(i)];
    val = rem(string2hash(member, "djb2"), length(B)) + 1;
    if B(val) == 0 % N�o precisa de altera��es para o Counting Filter pois j� verificava se n�o era 0
      ok = false;
      break
    end
  end
  
end