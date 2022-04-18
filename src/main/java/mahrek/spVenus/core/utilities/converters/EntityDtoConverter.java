package mahrek.spVenus.core.utilities.converters;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

public class EntityDtoConverter<F,T> {
    private ModelMapper modelMapper = new ModelMapper();
    private Class<T> toClass;

    public EntityDtoConverter(Class<T> toClass) {
        this.toClass = toClass;
    }

    public T convert(F from) {
//        modelMapper.getConfiguration().setAmbiguityIgnored(true);
//        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        T to = this.modelMapper.map(from, this.toClass);
        return to;
    }
}
