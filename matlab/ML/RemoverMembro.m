function BM = RemoverMembro(B, k, member)
  for i = 1:k
    member = [member num2str(i)];
    val = rem(string2hash(member, "djb2"), length(B)) + 1;
    if B(val) > 0
      B(val) -= 1;
      break
    end
  end
  BM = B;
end