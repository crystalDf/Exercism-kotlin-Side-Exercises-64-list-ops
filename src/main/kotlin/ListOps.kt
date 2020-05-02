fun <T> List<T>.customAppend(list: List<T>): List<T> =
        list.customFoldLeft(this) {
            acc, t -> acc.plus(t)
        }

@Suppress("UNCHECKED_CAST")
fun List<Any>.customConcat(): List<Any> =
        customFoldLeft(emptyList()) {
            acc, any -> when(any) {
                is Collection<*> -> acc.customAppend((any as List<Any>).customConcat())
                else -> acc.plus(any)
            }
        }

fun <T> List<T>.customFilter(predicate: (T) -> Boolean): List<T> =
        customFoldLeft(emptyList()) {
            acc, t -> if (predicate(t)) acc.plus(t) else acc
        }

val List<Any>.customSize: Int get() =
    customFoldLeft(0) {
        acc, _ -> acc + 1
    }

fun <T, U> List<T>.customMap(transform: (T) -> U): List<U> =
        customFoldLeft(emptyList()) {
            acc, t -> acc.plus(transform(t))
        }

fun <T, U> List<T>.customFoldLeft(initial: U, f: (U, T) -> U): U {

    var acc = initial

    for (element in this) acc = f(acc, element)

    return acc
}

fun <T, U> List<T>.customFoldRight(initial: U, f: (T, U) -> U): U =
        customReverse().customFoldLeft(initial) {
            acc, t -> f(t, acc)
        }

fun <T> List<T>.customReverse(): List<T> =
        customFoldLeft(emptyList()) {
            acc, t -> listOf(t).customAppend(acc)
        }
