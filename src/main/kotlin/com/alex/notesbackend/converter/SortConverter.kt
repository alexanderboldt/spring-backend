package com.alex.notesbackend.converter

import org.springframework.core.convert.converter.Converter
import org.springframework.data.domain.Sort

class SortConverter : Converter<String, Sort> {

    override fun convert(source: String): Sort {
        return source
            .split(",")
            .map { split ->
                when (split.startsWith("-")) {
                    true -> split.substring(1, split.length) to false
                    false -> split to true
                }
            }.map { (sortString, isAscending) ->
                Sort.Order(if (isAscending) Sort.Direction.ASC else Sort.Direction.DESC, sortString)
            }.let { sortList -> Sort.by(sortList ?: emptyList()) }
    }
}