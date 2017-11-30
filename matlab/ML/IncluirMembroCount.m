function B = IncluirMembroCount(B, k, member)
  for i = 1:k
    member = [member num2str(i)];
    val = rem(string2hash(member, "djb2"), length(B)) + 1;
    B(val) += 1;
  end
end