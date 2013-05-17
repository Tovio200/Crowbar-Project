package cbproject.crafting.recipes;

import net.minecraft.command.WrongUsageException;
import net.minecraft.item.ItemStack;

public class RecipeCrafter {
	public ItemStack[] input;
	public ItemStack output;
	public int heatRequired;
	public boolean isAdvancedRecipe = false;;
	
	public RecipeCrafter(ItemStack out, int heat, ItemStack...in) {
		if(in == null){
			System.err.println("dont register null!");
			return;
		}
		if(in.length > 3)
			throw new WrongUsageException("length must be within 3", input[0]);
		output = out;
		input = in;
		heatRequired = heat;
	}
	
	public RecipeCrafter setAdvancedRecipe(){
		isAdvancedRecipe = true;
		return this;
	}

	@Override
	public String toString(){
		String out = "[recipe:";
		for(ItemStack s : input)
			out.concat(" " + s.getDisplayName());
		out += " -> " + output.getDisplayName() + "]";
		return out;
	}
}