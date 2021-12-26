package dev.itsmeow.whisperwoods.client.renderer.entity.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.itsmeow.whisperwoods.entity.EntityZotzpyre;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;

/**
 * zotzpyre - cybercat5555
 * Created using Tabula 7.0.1
 */
public class ModelZotzpyre<T extends LivingEntity> extends EntityModel<T> {
    public ModelPart chest;
    public ModelPart stomach;
    public ModelPart neck;
    public ModelPart lWing01;
    public ModelPart rWing01;
    public ModelPart mane01;
    public ModelPart mane02;
    public ModelPart mane03;
    public ModelPart mane04;
    public ModelPart lLeg01;
    public ModelPart rLeg01;
    public ModelPart tail01;
    public ModelPart lLeg02;
    public ModelPart lClaw01;
    public ModelPart lClaw02;
    public ModelPart lClaw03;
    public ModelPart lClaw04;
    public ModelPart lClaw05;
    public ModelPart rLeg02;
    public ModelPart rClaw01;
    public ModelPart rClaw02;
    public ModelPart rClaw03;
    public ModelPart rClaw04;
    public ModelPart rClaw05;
    public ModelPart tail02;
    public ModelPart tail01Membrane;
    public ModelPart tail02Membrane;
    public ModelPart head;
    public ModelPart lEar01;
    public ModelPart rEar01;
    public ModelPart snout;
    public ModelPart lowerJaw;
    public ModelPart lEar02;
    public ModelPart rEar02;
    public ModelPart upperJaw01;
    public ModelPart upperJaw02;
    public ModelPart nose;
    public ModelPart lTeeth;
    public ModelPart rTeeth;
    public ModelPart lWing02;
    public ModelPart lWing03;
    public ModelPart lWingMembrane02;
    public ModelPart lWingMembrane03;
    public ModelPart lFinger;
    public ModelPart lWing04;
    public ModelPart lWingMembrane01;
    public ModelPart rWing02;
    public ModelPart rWing03;
    public ModelPart rWingMembrane02;
    public ModelPart rWingMembrane03;
    public ModelPart rFinger;
    public ModelPart rWing04;
    public ModelPart rWingMembrane01;

    public ModelZotzpyre() {
        this.texWidth = 128;
        this.texHeight = 64;
        this.snout = new ModelPart(this, 47, 0);
        this.snout.setPos(0.0F, -0.8F, -4.0F);
        this.snout.addBox(-1.5F, -0.5F, -3.0F, 3, 1, 3, 0.0F);
        this.setRotateAngle(snout, 0.2617993877991494F, 0.0F, 0.0F);
        this.rFinger = new ModelPart(this, 63, 18);
        this.rFinger.mirror = true;
        this.rFinger.setPos(-11.5F, 0.1F, -0.6F);
        this.rFinger.addBox(-1.0F, -0.5F, -5.0F, 2, 1, 5, 0.0F);
        this.setRotateAngle(rFinger, 0.0F, 0.40142572795869574F, -0.6283185307179586F);
        this.head = new ModelPart(this, 0, 52);
        this.head.setPos(0.0F, -0.2F, -1.5F);
        this.head.addBox(-4.0F, -3.5F, -4.5F, 8, 6, 5, 0.0F);
        this.setRotateAngle(head, -0.08726646259971647F, 0.0F, 0.0F);
        this.tail02Membrane = new ModelPart(this, 56, 56);
        this.tail02Membrane.setPos(0.0F, 0.0F, 0.0F);
        this.tail02Membrane.addBox(-3.5F, -0.0F, 0.0F, 7, 0, 6, 0.0F);
        this.lClaw01 = new ModelPart(this, 97, 0);
        this.lClaw01.setPos(0.8F, 7.8F, -0.8F);
        this.lClaw01.addBox(-0.3F, -1.0F, -0.5F, 4, 2, 1, 0.0F);
        this.setRotateAngle(lClaw01, 0.13962634015954636F, 0.6108652381980153F, 0.3490658503988659F);
        this.rClaw04 = new ModelPart(this, 97, 0);
        this.rClaw04.mirror = true;
        this.rClaw04.setPos(-1.2F, 7.8F, 0.6F);
        this.rClaw04.addBox(-3.7F, -1.0F, -0.5F, 4, 2, 1, 0.0F);
        this.setRotateAngle(rClaw04, 0.0F, 0.2617993877991494F, -0.3490658503988659F);
        this.lWing02 = new ModelPart(this, 43, 27);
        this.lWing02.setPos(5.7F, 0.0F, 0.0F);
        this.lWing02.addBox(0.0F, -1.0F, -1.0F, 12, 2, 2, 0.0F);
        this.setRotateAngle(lWing02, 0.3490658503988659F, 0.6981317007977318F, 0.7853981633974483F);
        this.rWingMembrane01 = new ModelPart(this, 7, 38);
        this.rWingMembrane01.mirror = true;
        this.rWingMembrane01.setPos(0.0F, 0.0F, -0.1F);
        this.rWingMembrane01.addBox(-27.7F, -0.01F, -2.8F, 29, 0, 13, 0.0F);
        this.setRotateAngle(rWingMembrane01, 0.0F, 0.2617993877991494F, 0.0F);
        this.rWing03 = new ModelPart(this, 43, 32);
        this.rWing03.mirror = true;
        this.rWing03.setPos(-12.0F, 0.0F, -0.1F);
        this.rWing03.addBox(-13.0F, -0.5F, -1.0F, 13, 1, 2, 0.0F);
        this.setRotateAngle(rWing03, 0.0F, 1.9547687622336491F, 0.0F);
        this.mane03 = new ModelPart(this, 30, 51);
        this.mane03.setPos(0.0F, 3.4F, -5.2F);
        this.mane03.addBox(-3.5F, 0.0F, -4.0F, 7, 3, 8, 0.0F);
        this.setRotateAngle(mane03, -0.05235987755982988F, 0.0F, 0.0F);
        this.rWingMembrane03 = new ModelPart(this, 64, 46);
        this.rWingMembrane03.mirror = true;
        this.rWingMembrane03.setPos(0.2F, 0.0F, 1.0F);
        this.rWingMembrane03.addBox(-6.0F, -0.04F, 0.0F, 16, 0, 18, 0.0F);
        this.setRotateAngle(rWingMembrane03, 0.0F, 0.40142572795869574F, 0.0F);
        this.lowerJaw = new ModelPart(this, 62, 8);
        this.lowerJaw.setPos(0.0F, 1.6F, -3.8F);
        this.lowerJaw.addBox(-1.5F, 0.0F, -2.7F, 3, 1, 3, 0.0F);
        this.rLeg02 = new ModelPart(this, 85, 8);
        this.rLeg02.mirror = true;
        this.rLeg02.setPos(-6.7F, 1.9F, 0.0F);
        this.rLeg02.addBox(-1.5F, 0.0F, -1.0F, 3, 9, 2, 0.0F);
        this.lWing04 = new ModelPart(this, 43, 36);
        this.lWing04.setPos(12.7F, 0.0F, -0.2F);
        this.lWing04.addBox(0.0F, -0.5F, -0.5F, 11, 1, 1, 0.0F);
        this.setRotateAngle(lWing04, 0.0F, -0.3490658503988659F, 0.0F);
        this.upperJaw02 = new ModelPart(this, 47, 6);
        this.upperJaw02.mirror = true;
        this.upperJaw02.setPos(-1.2F, 1.8F, 0.6F);
        this.upperJaw02.addBox(-1.0F, -1.0F, -3.8F, 2, 2, 4, 0.0F);
        this.setRotateAngle(upperJaw02, -0.2617993877991494F, -0.13962634015954636F, 0.0F);
        this.tail01Membrane = new ModelPart(this, 58, 52);
        this.tail01Membrane.setPos(0.0F, 0.0F, 0.0F);
        this.tail01Membrane.addBox(-3.5F, 0.0F, 0.0F, 7, 0, 4, 0.0F);
        this.rEar01 = new ModelPart(this, 0, 0);
        this.rEar01.mirror = true;
        this.rEar01.setPos(-2.5F, -2.6F, -1.0F);
        this.rEar01.addBox(-2.0F, -4.0F, -0.5F, 4, 4, 1, 0.0F);
        this.setRotateAngle(rEar01, 0.0F, 0.13962634015954636F, -0.3490658503988659F);
        this.nose = new ModelPart(this, 47, 14);
        this.nose.setPos(0.0F, 0.6F, -3.0F);
        this.nose.addBox(-1.5F, -3.0F, 0.0F, 3, 3, 0, 0.0F);
        this.setRotateAngle(nose, 0.10471975511965977F, 0.0F, 0.0F);
        this.rClaw01 = new ModelPart(this, 97, 0);
        this.rClaw01.mirror = true;
        this.rClaw01.setPos(-0.2F, 7.8F, -0.8F);
        this.rClaw01.addBox(-4.0F, -1.0F, -0.5F, 4, 2, 1, 0.0F);
        this.setRotateAngle(rClaw01, 0.13962634015954636F, -0.6108652381980153F, -0.3490658503988659F);
        this.rWing01 = new ModelPart(this, 43, 20);
        this.rWing01.mirror = true;
        this.rWing01.setPos(-4.5F, -2.2F, -7.0F);
        this.rWing01.addBox(-6.0F, -1.5F, -1.5F, 6, 3, 3, 0.0F);
        this.setRotateAngle(rWing01, -0.13962634015954636F, 0.5759586531581287F, -0.40142572795869574F);
        this.tail01 = new ModelPart(this, 30, 19);
        this.tail01.setPos(0.0F, -3.3F, 7.9F);
        this.tail01.addBox(-1.0F, -0.5F, 0.0F, 2, 1, 4, 0.0F);
        this.setRotateAngle(tail01, -0.6283185307179586F, 0.0F, 0.0F);
        this.upperJaw01 = new ModelPart(this, 47, 6);
        this.upperJaw01.setPos(1.2F, 1.8F, 0.6F);
        this.upperJaw01.addBox(-1.0F, -1.0F, -3.8F, 2, 2, 4, 0.0F);
        this.setRotateAngle(upperJaw01, -0.2617993877991494F, 0.13962634015954636F, 0.0F);
        this.mane04 = new ModelPart(this, 102, 25);
        this.mane04.setPos(0.0F, 2.1F, -10.6F);
        this.mane04.addBox(-2.5F, -0.8F, -0.5F, 5, 6, 2, 0.0F);
        this.setRotateAngle(mane04, 0.08726646259971647F, 0.0F, 0.0F);
        this.mane01 = new ModelPart(this, 102, 13);
        this.mane01.setPos(0.0F, -3.6F, -10.3F);
        this.mane01.addBox(-2.5F, -0.8F, 0.0F, 5, 2, 8, 0.0F);
        this.setRotateAngle(mane01, 0.2617993877991494F, 0.0F, 0.0F);
        this.rWingMembrane02 = new ModelPart(this, 52, 19);
        this.rWingMembrane02.mirror = true;
        this.rWingMembrane02.setPos(-5.1F, 0.0F, 0.0F);
        this.rWingMembrane02.addBox(-6.0F, -0.0F, 0.0F, 12, 0, 26, 0.0F);
        this.setRotateAngle(rWingMembrane02, 0.0F, 0.5759586531581287F, 0.0F);
        this.rClaw02 = new ModelPart(this, 97, 0);
        this.rClaw02.mirror = true;
        this.rClaw02.setPos(-1.2F, 7.8F, -0.6F);
        this.rClaw02.addBox(-3.7F, -1.0F, -0.5F, 4, 2, 1, 0.0F);
        this.setRotateAngle(rClaw02, 0.0F, -0.2617993877991494F, -0.3490658503988659F);
        this.rClaw05 = new ModelPart(this, 97, 0);
        this.rClaw05.mirror = true;
        this.rClaw05.setPos(-0.8F, 7.8F, 0.8F);
        this.rClaw05.addBox(-3.7F, -1.0F, -0.5F, 4, 2, 1, 0.0F);
        this.setRotateAngle(rClaw05, -0.13962634015954636F, 0.6108652381980153F, -0.3490658503988659F);
        this.lClaw04 = new ModelPart(this, 97, 0);
        this.lClaw04.setPos(1.2F, 7.8F, 0.6F);
        this.lClaw04.addBox(-0.3F, -1.0F, -0.5F, 4, 2, 1, 0.0F);
        this.setRotateAngle(lClaw04, 0.0F, -0.2617993877991494F, 0.3490658503988659F);
        this.lEar01 = new ModelPart(this, 0, 0);
        this.lEar01.setPos(2.5F, -2.6F, -1.0F);
        this.lEar01.addBox(-2.0F, -4.0F, -0.5F, 4, 4, 1, 0.0F);
        this.setRotateAngle(lEar01, 0.0F, -0.13962634015954636F, 0.3490658503988659F);
        this.lClaw05 = new ModelPart(this, 97, 0);
        this.lClaw05.setPos(0.8F, 7.8F, 0.8F);
        this.lClaw05.addBox(-0.3F, -1.0F, -0.5F, 4, 2, 1, 0.0F);
        this.setRotateAngle(lClaw05, -0.13962634015954636F, -0.6108652381980153F, 0.3490658503988659F);
        this.lLeg02 = new ModelPart(this, 85, 8);
        this.lLeg02.setPos(6.7F, 1.9F, 0.0F);
        this.lLeg02.addBox(-1.5F, 0.0F, -1.0F, 3, 9, 2, 0.0F);
        this.lLeg01 = new ModelPart(this, 73, 0);
        this.lLeg01.setPos(3.2F, -1.6F, 5.6F);
        this.lLeg01.addBox(-0.5F, -2.0F, -1.5F, 9, 4, 3, 0.0F);
        this.setRotateAngle(lLeg01, 0.0F, 0.45378560551852565F, -0.03490658503988659F);
        this.mane02 = new ModelPart(this, 98, 3);
        this.mane02.setPos(0.0F, -4.0F, -9.0F);
        this.mane02.addBox(-3.5F, -0.7F, 0.0F, 7, 2, 8, 0.0F);
        this.setRotateAngle(mane02, 0.12217304763960307F, 0.0F, 0.0F);
        this.lWingMembrane02 = new ModelPart(this, 52, 19);
        this.lWingMembrane02.setPos(5.1F, 0.0F, 0.0F);
        this.lWingMembrane02.addBox(-6.0F, -0.0F, 0.0F, 12, 0, 26, 0.0F);
        this.setRotateAngle(lWingMembrane02, 0.0F, -0.5759586531581287F, 0.0F);
        this.tail02 = new ModelPart(this, 31, 24);
        this.tail02.setPos(0.0F, 0.0F, 3.8F);
        this.tail02.addBox(-0.5F, -0.5F, 0.0F, 1, 1, 4, 0.0F);
        this.setRotateAngle(tail02, -0.3141592653589793F, 0.0F, 0.0F);
        this.rWing02 = new ModelPart(this, 43, 27);
        this.rWing02.mirror = true;
        this.rWing02.setPos(-5.7F, 0.0F, 0.0F);
        this.rWing02.addBox(-12.0F, -1.0F, -1.0F, 12, 2, 2, 0.0F);
        this.setRotateAngle(rWing02, 0.3490658503988659F, -0.6981317007977318F, -0.7853981633974483F);
        this.rEar02 = new ModelPart(this, 33, 0);
        this.rEar02.mirror = true;
        this.rEar02.setPos(-0.9F, -0.5F, 0.8F);
        this.rEar02.addBox(-1.5F, -5.5F, -0.5F, 3, 6, 1, 0.0F);
        this.setRotateAngle(rEar02, 0.20943951023931953F, 0.0F, 0.17453292519943295F);
        this.chest = new ModelPart(this, 0, 0);
        this.chest.setPos(0.0F, 14.8F, 2.5F);
        this.chest.addBox(-5.0F, -4.5F, -10.0F, 10, 8, 10, 0.0F);
        this.setRotateAngle(chest, 0.05235987755982988F, 0.0F, 0.0F);
        this.lWing01 = new ModelPart(this, 43, 20);
        this.lWing01.setPos(4.5F, -2.2F, -7.0F);
        this.lWing01.addBox(0.0F, -1.5F, -1.5F, 6, 3, 3, 0.0F);
        this.setRotateAngle(lWing01, -0.13962634015954636F, -0.5759586531581287F, 0.40142572795869574F);
        this.lWing03 = new ModelPart(this, 43, 32);
        this.lWing03.setPos(12.0F, 0.0F, -0.1F);
        this.lWing03.addBox(0.0F, -0.5F, -1.0F, 13, 1, 2, 0.0F);
        this.setRotateAngle(lWing03, 0.0F, -1.9547687622336491F, 0.0F);
        this.lWingMembrane01 = new ModelPart(this, 7, 38);
        this.lWingMembrane01.setPos(0.0F, 0.0F, -0.1F);
        this.lWingMembrane01.addBox(-1.3F, -0.01F, -2.8F, 29, 0, 13, 0.0F);
        this.setRotateAngle(lWingMembrane01, 0.0F, -0.2617993877991494F, 0.0F);
        this.stomach = new ModelPart(this, 0, 23);
        this.stomach.setPos(0.0F, -0.2F, -0.8F);
        this.stomach.addBox(-4.5F, -4.0F, 0.0F, 9, 7, 8, 0.0F);
        this.setRotateAngle(stomach, -0.13962634015954636F, 0.0F, 0.0F);
        this.rClaw03 = new ModelPart(this, 97, 0);
        this.rClaw03.mirror = true;
        this.rClaw03.setPos(-1.3F, 7.7F, 0.0F);
        this.rClaw03.addBox(-3.7F, -1.0F, -0.5F, 4, 2, 1, 0.0F);
        this.setRotateAngle(rClaw03, 0.0F, 0.0F, -0.3490658503988659F);
        this.rLeg01 = new ModelPart(this, 73, 0);
        this.rLeg01.mirror = true;
        this.rLeg01.setPos(-3.2F, -1.6F, 5.6F);
        this.rLeg01.addBox(-8.5F, -2.0F, -1.5F, 9, 4, 3, 0.0F);
        this.setRotateAngle(rLeg01, 0.0F, -0.45378560551852565F, 0.03490658503988659F);
        this.rTeeth = new ModelPart(this, 63, 0);
        this.rTeeth.mirror = true;
        this.rTeeth.setPos(-0.4F, 1.3F, -0.8F);
        this.rTeeth.addBox(-0.5F, -1.1F, -2.8F, 1, 2, 2, 0.0F);
        this.setRotateAngle(rTeeth, 0.13962634015954636F, 0.0F, 0.0F);
        this.lClaw02 = new ModelPart(this, 97, 0);
        this.lClaw02.setPos(1.2F, 7.8F, -0.6F);
        this.lClaw02.addBox(-0.3F, -1.0F, -0.5F, 4, 2, 1, 0.0F);
        this.setRotateAngle(lClaw02, 0.0F, 0.2617993877991494F, 0.3490658503988659F);
        this.lTeeth = new ModelPart(this, 63, 0);
        this.lTeeth.setPos(0.4F, 1.3F, -0.8F);
        this.lTeeth.addBox(-0.5F, -1.1F, -2.8F, 1, 2, 2, 0.0F);
        this.setRotateAngle(lTeeth, 0.13962634015954636F, 0.0F, 0.0F);
        this.rWing04 = new ModelPart(this, 43, 36);
        this.rWing04.mirror = true;
        this.rWing04.setPos(-12.7F, 0.0F, -0.2F);
        this.rWing04.addBox(-11.0F, -0.5F, -0.5F, 11, 1, 1, 0.0F);
        this.setRotateAngle(rWing04, 0.0F, 0.3490658503988659F, 0.0F);
        this.lClaw03 = new ModelPart(this, 97, 0);
        this.lClaw03.setPos(1.3F, 7.7F, 0.0F);
        this.lClaw03.addBox(-0.3F, -1.0F, -0.5F, 4, 2, 1, 0.0F);
        this.setRotateAngle(lClaw03, 0.0F, 0.0F, 0.3490658503988659F);
        this.lFinger = new ModelPart(this, 63, 18);
        this.lFinger.setPos(11.5F, 0.1F, -0.6F);
        this.lFinger.addBox(-1.0F, -0.5F, -5.0F, 2, 1, 5, 0.0F);
        this.setRotateAngle(lFinger, 0.0F, -0.40142572795869574F, 0.6283185307179586F);
        this.lEar02 = new ModelPart(this, 33, 0);
        this.lEar02.setPos(0.9F, -0.5F, 0.8F);
        this.lEar02.addBox(-1.5F, -5.5F, -0.5F, 3, 6, 1, 0.0F);
        this.setRotateAngle(lEar02, 0.20943951023931953F, 0.0F, -0.17453292519943295F);
        this.neck = new ModelPart(this, 0, 41);
        this.neck.setPos(0.0F, -0.8F, -9.6F);
        this.neck.addBox(-3.5F, -3.5F, -2.0F, 7, 6, 2, 0.0F);
        this.setRotateAngle(neck, 0.08726646259971647F, 0.0F, 0.0F);
        this.lWingMembrane03 = new ModelPart(this, 64, 46);
        this.lWingMembrane03.setPos(0.2F, 0.0F, 1.0F);
        this.lWingMembrane03.addBox(-10.0F, -0.04F, 0.0F, 16, 0, 18, 0.0F);
        this.setRotateAngle(lWingMembrane03, 0.0F, -0.40142572795869574F, 0.0F);
        this.head.addChild(this.snout);
        this.rWing02.addChild(this.rFinger);
        this.neck.addChild(this.head);
        this.tail02.addChild(this.tail02Membrane);
        this.lLeg02.addChild(this.lClaw01);
        this.rLeg02.addChild(this.rClaw04);
        this.lWing01.addChild(this.lWing02);
        this.rWing03.addChild(this.rWingMembrane01);
        this.rWing02.addChild(this.rWing03);
        this.chest.addChild(this.mane03);
        this.rWing02.addChild(this.rWingMembrane03);
        this.head.addChild(this.lowerJaw);
        this.rLeg01.addChild(this.rLeg02);
        this.lWing03.addChild(this.lWing04);
        this.snout.addChild(this.upperJaw02);
        this.tail01.addChild(this.tail01Membrane);
        this.head.addChild(this.rEar01);
        this.snout.addChild(this.nose);
        this.rLeg02.addChild(this.rClaw01);
        this.chest.addChild(this.rWing01);
        this.stomach.addChild(this.tail01);
        this.snout.addChild(this.upperJaw01);
        this.chest.addChild(this.mane04);
        this.chest.addChild(this.mane01);
        this.rWing02.addChild(this.rWingMembrane02);
        this.rLeg02.addChild(this.rClaw02);
        this.rLeg02.addChild(this.rClaw05);
        this.lLeg02.addChild(this.lClaw04);
        this.head.addChild(this.lEar01);
        this.lLeg02.addChild(this.lClaw05);
        this.lLeg01.addChild(this.lLeg02);
        this.stomach.addChild(this.lLeg01);
        this.chest.addChild(this.mane02);
        this.lWing02.addChild(this.lWingMembrane02);
        this.tail01.addChild(this.tail02);
        this.rWing01.addChild(this.rWing02);
        this.rEar01.addChild(this.rEar02);
        this.chest.addChild(this.lWing01);
        this.lWing02.addChild(this.lWing03);
        this.lWing03.addChild(this.lWingMembrane01);
        this.chest.addChild(this.stomach);
        this.rLeg02.addChild(this.rClaw03);
        this.stomach.addChild(this.rLeg01);
        this.upperJaw02.addChild(this.rTeeth);
        this.lLeg02.addChild(this.lClaw02);
        this.upperJaw01.addChild(this.lTeeth);
        this.rWing03.addChild(this.rWing04);
        this.lLeg02.addChild(this.lClaw03);
        this.lWing02.addChild(this.lFinger);
        this.lEar01.addChild(this.lEar02);
        this.chest.addChild(this.neck);
        this.lWing02.addChild(this.lWingMembrane03);
    }

    @Override
    public void renderToBuffer(PoseStack matrixStackIn, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        this.chest.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
    }

    @Override
    public void setRotationAngles(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        if(entity instanceof EntityZotzpyre) {
            EntityZotzpyre zotz = (EntityZotzpyre) entity;
            if((zotz.getDeltaMovement().x() > 0.05 || zotz.getDeltaMovement().z() > 0.05) && zotz.getVehicle() == null || (zotz.getVehicle() == null && zotz.hasImpulse)) {
                this.setRotateAngle(lLeg01, 1.3962634015954636F, 0.08726646259971647F, 0.40142572795869574F);
                this.setRotateAngle(lWing03, 0.0F, -0.9599310885968813F, 0.0F);
                this.setRotateAngle(lWing02, 0.0F, 0.5235987755982988F, 0.0F);
                this.setRotateAngle(rLeg01, 1.3962634015954636F, -0.08726646259971647F, -0.40142572795869574F);
                this.setRotateAngle(rWing03, 0.0F, 0.9599310885968813F, 0.0F);
                this.setRotateAngle(rWing02, 0.0F, -0.5235987755982988F, 0.0F);

                //
                this.setRotateAngle(tail01, 0.0F, 0.0F, 0.0F);
                this.setRotateAngle(tail02, 0.0F, 0.0F, 0.0F);
                this.setRotateAngle(rWingMembrane02, 0.0F, 0.0F, 0.0F);
                this.setRotateAngle(lWingMembrane02, 0.0F, 0.0F, 0.0F);
                this.setRotateAngle(lWing01, 0.0F, 0.0F, 0.0F);
                this.setRotateAngle(rWing01, 0.0F, 0.0F, 0.0F);

                //
                this.setRotateAngle(head, -0.08726646259971647F, 0.0F, 0.0F);
            } else {
                this.setRotateAngle(lWing02, 0.3490658503988659F, 0.6981317007977318F, 0.7853981633974483F);
                this.setRotateAngle(rWing03, 0.0F, 1.9547687622336491F, 0.0F);
                this.setRotateAngle(rWing01, -0.13962634015954636F, 0.5759586531581287F, -0.40142572795869574F);
                this.setRotateAngle(tail01, -0.6283185307179586F, 0.0F, 0.0F);
                this.setRotateAngle(rWingMembrane02, 0.0F, 0.5759586531581287F, 0.0F);
                this.setRotateAngle(lLeg01, 0.0F, 0.45378560551852565F, -0.03490658503988659F);
                this.setRotateAngle(lWingMembrane02, 0.0F, -0.5759586531581287F, 0.0F);
                this.setRotateAngle(tail02, -0.3141592653589793F, 0.0F, 0.0F);
                this.setRotateAngle(rWing02, 0.3490658503988659F, -0.6981317007977318F, -0.7853981633974483F);
                this.setRotateAngle(lWing01, -0.13962634015954636F, -0.5759586531581287F, 0.40142572795869574F);
                this.setRotateAngle(lWing03, 0.0F, -1.9547687622336491F, 0.0F);
                this.setRotateAngle(rLeg01, 0.0F, -0.45378560551852565F, 0.03490658503988659F);
                this.lLeg01.xRot = Mth.sin(limbSwing * 0.8665F + (float) Math.PI) * limbSwingAmount;
                this.rLeg01.xRot = Mth.cos(limbSwing * 0.8665F) * limbSwingAmount;
                this.lWing01.xRot = Mth.sin(limbSwing * 0.8665F + (float) Math.PI) * limbSwingAmount - 0.13962634015954636F;
                this.rWing01.xRot = Mth.cos(limbSwing * 0.8665F) * limbSwingAmount - 0.13962634015954636F;
            }
            if(entity.getVehicle() != null) {
                this.lowerJaw.xRot = (float) Math.toRadians(45);
            } else {
                this.lowerJaw.xRot = 0F;
            }
            this.neck.xRot = (entity.xRotO + (entity.xRot - entity.xRotO) * Minecraft.getInstance().getFrameTime()) * 0.017453292F;
            float yawOffset = interpolate(entity.yBodyRotO, entity.yBodyRot, Minecraft.getInstance().getFrameTime());
            float yawHead = interpolate(entity.yHeadRotO, entity.yHeadRot, Minecraft.getInstance().getFrameTime());
            this.neck.yRot = (yawHead - yawOffset) * 0.017453292F;
        }
    }

    private static float interpolate(float a1, float a2, float p) {
        float angle = a2 - a1;
        angle = angle < -180F ? angle += 360F : angle;
        return a1 + p * (angle = angle >= 180F ? angle -= 360F : angle);
    }

    public void setRotateAngle(ModelPart ModelRenderer, float x, float y, float z) {
        ModelRenderer.xRot = x;
        ModelRenderer.yRot = y;
        ModelRenderer.zRot = z;
    }
}
