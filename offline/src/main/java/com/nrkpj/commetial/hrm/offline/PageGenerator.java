package com.nrkpj.commetial.hrm.offline;

import java.io.IOException;
import java.util.Collection;

public interface PageGenerator<T> {

    void generate(Collection<T> items) throws IOException;
}
