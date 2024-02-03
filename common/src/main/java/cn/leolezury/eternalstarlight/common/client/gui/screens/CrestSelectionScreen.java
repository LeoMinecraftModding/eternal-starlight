package cn.leolezury.eternalstarlight.common.client.gui.screens;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.client.gui.button.UnvisibleButton;
import cn.leolezury.eternalstarlight.common.crests.util.CrestRegister;
import cn.leolezury.eternalstarlight.common.crests.util.CrestType;
import cn.leolezury.eternalstarlight.common.crests.util.CrestUtil;
import cn.leolezury.eternalstarlight.common.util.ESUtil;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

import java.util.List;

@Environment(EnvType.CLIENT)
public class CrestSelectionScreen extends Screen {
    private static final ResourceLocation BACKGROUND = new ResourceLocation(EternalStarlight.MOD_ID, "textures/gui/screen/crest_selection.png");
    private static final ResourceLocation ACTIVE_NEXT_PAGE_BUTTON = new ResourceLocation(EternalStarlight.MOD_ID, "textures/gui/screen/active_next_page_button.png");
    private static final ResourceLocation ACTIVE_LAST_PAGE_BUTTON = new ResourceLocation(EternalStarlight.MOD_ID, "textures/gui/screen/active_last_page_button.png");
    private static final ResourceLocation NEXT_PAGE_BUTTON = new ResourceLocation(EternalStarlight.MOD_ID, "textures/gui/screen/next_page_button.png");
    private static final ResourceLocation LAST_PAGE_BUTTON = new ResourceLocation(EternalStarlight.MOD_ID, "textures/gui/screen/last_page_button.png");
    private static final int WIDTH = 256;
    private static final int HEIGHT = 256;
    private final Player player;
    private static final int GUI_RATIO = WIDTH / HEIGHT;
    public UnvisibleButton slot_1;
    public UnvisibleButton slot_2;
    public UnvisibleButton slot_3;
    public UnvisibleButton slot_4;
    public UnvisibleButton slot_5;
    public UnvisibleButton main_crest;
    public UnvisibleButton next_page;
    public UnvisibleButton last_page;
    private int selectNumber;
    //    private int selectOrdinary;
    private List<CrestType> types;
    public CrestSelectionScreen(Player player) {
        super(Component.empty());
        this.player = player;
        selectNumber = ESUtil.getPersistentData(this.player).getInt("SelectNumber");
//        selectOrdinary = ESUtil.getPersistentData(this.player).getInt("SelectOrdinary");
        types = CrestRegister.CRESTS.values().stream().toList();
    }

    @Override
    protected void init() {
        ESUtil.getPersistentData(player).putInt("PlayerCrestsCount", 6);
        this.last_page = this.addRenderableWidget(new UnvisibleButton(this.width / 4 - 50, this.height / 2 - 84, 50, 24, Component.empty(), (button -> turnPageLeft())));
        this.next_page = this.addRenderableWidget(new UnvisibleButton(this.width / 4 - 50, this.height / 2 + 52, 50, 24, Component.empty(), (button -> turnPageRight())));
        this.slot_1 = this.addRenderableWidget(new UnvisibleButton(this.width / 2 - 16, this.height - 242, 58, 58, Component.empty(), (button -> pressCrestSlot(0))));
        this.slot_2 = this.addRenderableWidget(new UnvisibleButton(this.width / 2 - 74, this.height - 191, 58, 58, Component.empty(), (button -> pressCrestSlot(1))));
        this.slot_3 = this.addRenderableWidget(new UnvisibleButton(this.width / 2 + 38, this.height - 191, 58, 58, Component.empty(), (button -> pressCrestSlot(2))));
        this.slot_4 = this.addRenderableWidget(new UnvisibleButton(this.width / 2 - 54, this.height - 121, 58, 58, Component.empty(), (button -> pressCrestSlot(3))));
        this.slot_5 = this.addRenderableWidget(new UnvisibleButton(this.width / 2 + 15, this.height - 121, 59, 58, Component.empty(), (button -> pressCrestSlot(4))));
        this.main_crest = this.addRenderableWidget(new UnvisibleButton(this.width / 4 - 62, this.height / 2 - 40, 72, 72, Component.empty(), (button -> pressCrestButton())));
        this.updateButtonStatus();
    }

    public void updateButtonStatus() {
        this.next_page.active = !(CrestUtil.getCrestsCount(this.player) - 1 == this.selectNumber);
        this.last_page.active = this.selectNumber >= 1;
        this.main_crest.active = true;
        slot_1.active = ESUtil.getPersistentData(this.player).getBoolean("PressCrestAble_0");
        slot_2.active = ESUtil.getPersistentData(this.player).getBoolean("PressCrestAble_1");
        slot_3.active = ESUtil.getPersistentData(this.player).getBoolean("PressCrestAble_2");
        slot_4.active = ESUtil.getPersistentData(this.player).getBoolean("PressCrestAble_3");
        slot_5.active = ESUtil.getPersistentData(this.player).getBoolean("PressCrestAble_4");
    }

    private ResourceLocation slotTextures(int ordinary) {
        if (ESUtil.getPersistentData(this.player).getBoolean("PressCrestAble" + "_" + ordinary)) {
            return new ResourceLocation(EternalStarlight.MOD_ID, "textures/gui/screen/equipped_slot.png");
        }
        return new ResourceLocation(EternalStarlight.MOD_ID, "textures/gui/screen/empty_crest_slot.png");
    }

    private boolean crestEquipped(CrestType type) {
        return ESUtil.getPersistentData(this.player).getBoolean( type.name + "Equipped");
    }

    private void turnPageRight() {
        ESUtil.getPersistentData(this.player).putInt("SelectNumber", selectNumber + 1);
        System.out.println(CrestUtil.getCrestsCount(this.player));
    }

    private void turnPageLeft() {
        ESUtil.getPersistentData(this.player).putInt("SelectNumber", selectNumber - 1);
        System.out.println(CrestUtil.getCrestsCount(this.player));
    }

    @Override
    public void renderBackground(GuiGraphics guiGraphics, int i, int j, float f) {
        super.renderBackground(guiGraphics, i, j, f);
        int currentGuiRatio = this.width / this.height * 2; // weird thing
        if (currentGuiRatio > GUI_RATIO) {
            guiGraphics.blit(BACKGROUND, 0, (this.height - this.width / GUI_RATIO) / 2, 0.0F, 0.0F, this.width, this.width / GUI_RATIO, this.width, this.width / GUI_RATIO);
        } else {
            guiGraphics.blit(BACKGROUND, (this.width - this.height * GUI_RATIO) / 2, 0, 0.0F, 0.0F, this.height * GUI_RATIO, this.height, this.height * GUI_RATIO, this.height);
        }
    }

    @Override
    public void render(GuiGraphics guiGraphics, int i, int j, float f) {
        super.render(guiGraphics, i, j, f);
        if (this.next_page.active) {
            guiGraphics.blit(ACTIVE_NEXT_PAGE_BUTTON, this.width / 4 - 50, this.height / 2 + 52 , 0.0F, 0.0F, 50, 24, 50, 24);
        } else {
            guiGraphics.blit(NEXT_PAGE_BUTTON, this.width / 4 - 50, this.height / 2 + 52, 0.0F, 0.0F, 50, 24, 50, 24);
        }
        if (this.last_page.active) {
            guiGraphics.blit(ACTIVE_LAST_PAGE_BUTTON, this.width / 4 - 50, this.height / 2 - 84, 0.0F, 0.0F, 50, 24, 50, 24);
        } else {
            guiGraphics.blit(LAST_PAGE_BUTTON, this.width / 4 - 50, this.height / 2 - 84, 0.0F, 0.0F, 50, 24, 50, 24);
        }
        guiGraphics.blit(new ResourceLocation(EternalStarlight.MOD_ID, "textures/gui/screen/empty_crest_slot.png"), this.width / 4 - 62, this.height / 2 - 40, 0.0F, 0.0F, 72, 72, 72, 72);
        if (CrestUtil.getCrestsCount(this.player) > 0) {
            guiGraphics.blit(CrestUtil.getCrestPath(CrestRegister.CRESTS.values().stream().toList().get(ESUtil.getPersistentData(this.player).getInt("SelectNumber"))), this.width / 4 - 62, this.height / 2 - 40, 0.0F, 0.0F, 72, 72, 72, 72);
        }
        guiGraphics.blit(slotTextures(0), this.width / 2 - 23, this.height - 249, 0.0F, 0.0F, 72, 72, 72, 72);
        guiGraphics.blit(slotTextures(1), this.width / 2 - 80, this.height - 197, 0.0F, 0.0F, 72, 72, 72, 72);
        guiGraphics.blit(slotTextures(2), this.width / 2 + 32, this.height - 197, 0.0F, 0.0F, 72, 72, 72, 72);
        guiGraphics.blit(slotTextures(3), this.width / 2 - 60, this.height - 130, 0.0F, 0.0F, 72, 72, 72, 72);
        guiGraphics.blit(slotTextures(4), this.width / 2 + 11, this.height - 130, 0.0F, 0.0F, 72, 72, 72, 72);
        if (!types.isEmpty()) {
            if (ESUtil.getPersistentData(this.player).getBoolean("PressCrestAble_0")) {
                String typeName = ESUtil.getPersistentData(this.player).getString("EquippedCrest_0");
                guiGraphics.blit(CrestUtil.getCrestPath(CrestUtil.getType(typeName)), this.width / 2 - 23, this.height - 249, 0.0F, 0.0F, 72, 72, 72, 72);
            }
            if (ESUtil.getPersistentData(this.player).getBoolean("PressCrestAble_1")) {
                String typeName = ESUtil.getPersistentData(this.player).getString("EquippedCrest_1");
                guiGraphics.blit(CrestUtil.getCrestPath(CrestUtil.getType(typeName)), this.width / 2 - 80, this.height - 197, 0.0F, 0.0F, 72, 72, 72, 72);
            }
            if (ESUtil.getPersistentData(this.player).getBoolean("PressCrestAble_2")) {
                String typeName = ESUtil.getPersistentData(this.player).getString("EquippedCrest_2");
                guiGraphics.blit(CrestUtil.getCrestPath(CrestUtil.getType(typeName)), this.width / 2 + 32, this.height - 197, 0.0F, 0.0F, 72, 72, 72, 72);
            }
            if (ESUtil.getPersistentData(this.player).getBoolean("PressCrestAble_3")) {
                String typeName = ESUtil.getPersistentData(this.player).getString("EquippedCrest_3");
                guiGraphics.blit(CrestUtil.getCrestPath(CrestUtil.getType(typeName)), this.width / 2 - 60, this.height - 130, 0.0F, 0.0F, 72, 72, 72, 72);
            }
            if (ESUtil.getPersistentData(this.player).getBoolean("PressCrestAble_4")) {
                String typeName = ESUtil.getPersistentData(this.player).getString("EquippedCrest_4");
                guiGraphics.blit(CrestUtil.getCrestPath(CrestUtil.getType(typeName)), this.width / 2 + 11, this.height - 130, 0.0F, 0.0F, 72, 72, 72, 72);
            }
        }
    }

    public void pressCrestSlot(int ordinary) {
        int i = ESUtil.getPersistentData(this.player).getInt("CrestOrdinary");
        int s = ordinary;
        int m = i;
        if (ordinary < 5 && i > ordinary) {
            for (int c = i; c > ordinary; c = c - 1 ) {
                ESUtil.getPersistentData(this.player).putBoolean("PressCrestAble_" + m, false);
                ESUtil.getPersistentData(this.player).putBoolean("PressCrestAble_" + s, true);
                ESUtil.getPersistentData(this.player).putString("EquippedCrest_" + s, ESUtil.getPersistentData(this.player).getString("EquippedCrest_" + (s + 1)));
                if ( s + 1 != m) {
                    s = s + 1;
                    m = m - 1;
                }
                ESUtil.getPersistentData(this.player).putInt("CrestOrdinary", i - 1);
                i = ESUtil.getPersistentData(this.player).getInt("CrestOrdinary");
            }
        } else {
            ESUtil.getPersistentData(this.player).putBoolean("PressCrestAble" + "_" + ordinary, false);
        }
    }

    public void pressCrestButton() {
        int ordinary = ESUtil.getPersistentData(this.player).getInt("CrestOrdinary");
        System.out.println(ordinary);
        for (int crestSlotOrdinary = 0; crestSlotOrdinary <= ordinary; crestSlotOrdinary = crestSlotOrdinary + 1) {
            if (crestSlotOrdinary == ordinary && ordinary < 5 && !types.isEmpty()) {
                ESUtil.getPersistentData(this.player).putBoolean("PressCrestAble" + "_" + crestSlotOrdinary, true);
                ESUtil.getPersistentData(this.player).putInt("CrestOrdinary", ordinary + 1);
                ESUtil.getPersistentData(this.player).putString("EquippedCrest_" + ordinary, types.get(this.selectNumber).name);
                System.out.println(ESUtil.getPersistentData(this.player).getString("EquippedCrest_" + ordinary));
                System.out.println(crestSlotOrdinary);
                System.out.println(this.selectNumber);
                break;
            }
        }
    }
}
