F := GF(2);
R<X> := PolynomialRing(F);


for d := 2 to 12 do
    n := d-1;
    G := GeneralLinearGroup(2*n, F);
    cnt := 0;
    for i := 0 to (2^(n-1)-1) do
        U := IntegerToSequence(i, 2, n-1);
        Insert(~U, 1, 1);
        Append(~U, 1);
        P := R ! U;
        for j := (i+1) to (2^(n-1)-1) do
            V := IntegerToSequence(j, 2, n-1);
	    Insert(~V, 1, 1);
	    Append(~V, 1);
	    Q := R ! V;
            if (GCD(P,Q) eq 1) then
	        M := SylvesterMatrix(P,Q);
	        if(Order(M) eq (2^(2*n) - 1)) then
	            //print([P,Q]);
		    cnt := cnt + 1;
	        end if;
	    end if;
        end for;
    end for;
    print([d, cnt]);
end for;
quit;
