package me.faln.chunkhopper.objects;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;

import java.math.BigInteger;
import java.util.*;

@Getter @Setter
public class CHopper {

    private Map<Material, BigInteger> storedItems = new HashMap<>();

    private final UUID id;

    public CHopper(final UUID id) {
        this.id = id;
    }

    public void addItem(final Material material, final BigInteger amount) {
        if (!this.contains(material)) {
            this.storedItems.put(material, amount);
            return;
        }
        this.storedItems.replace(material, this.storedItems.get(material).add(amount));
    }

    public boolean contains(final Material material) {
        return this.storedItems.containsKey(material);
    }

}
